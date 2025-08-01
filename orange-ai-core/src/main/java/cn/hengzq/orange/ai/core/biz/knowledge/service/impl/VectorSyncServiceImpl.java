package cn.hengzq.orange.ai.core.biz.knowledge.service.impl;

import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import cn.hengzq.orange.ai.common.biz.knowledge.constant.DocStatusEnum;
import cn.hengzq.orange.ai.common.biz.knowledge.constant.KnowledgeConstant;
import cn.hengzq.orange.ai.common.biz.knowledge.constant.SliceEmbStatus;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.BaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.DocVO;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import cn.hengzq.orange.ai.core.biz.embedding.service.EmbeddingModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.ChunkEntity;
import cn.hengzq.orange.ai.core.biz.knowledge.mapper.ChunkMapper;
import cn.hengzq.orange.ai.core.biz.knowledge.service.BaseService;
import cn.hengzq.orange.ai.core.biz.knowledge.service.DocService;
import cn.hengzq.orange.ai.core.biz.knowledge.service.VectorSyncService;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.ai.core.biz.vectorstore.service.VectorStoreServiceFactory;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.common.util.CollUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class VectorSyncServiceImpl implements VectorSyncService {

    private final ModelService modelService;

    private final VectorStoreServiceFactory vectorStoreServiceFactory;

    private final EmbeddingModelServiceFactory embeddingModelServiceFactory;

    private final BaseService baseService;

    private final DocService docService;

    private final ChunkMapper chunkMapper;


    @Override
    public void refreshDocByDocIds(List<String> docIds) {
        Map<String, DocVO> docMap = docService.mapKnowledgeDocByIds(docIds);
        if (CollUtil.isEmpty(docMap)) {
            log.warn("docMap is empty.docIds:{}", docIds);
            return;
        }
        for (String docId : docIds) {
            if (!docMap.containsKey(docId) || Objects.isNull(docMap.get(docId))) {
                continue;
            }
            refreshDoc(docMap.get(docId), false);
        }
    }

    @Override
    public void forcedRefreshDocByDocIds(List<String> docIds) {
        Map<String, DocVO> docMap = docService.mapKnowledgeDocByIds(docIds);
        if (CollUtil.isEmpty(docMap)) {
            log.warn("docMap is empty.docIds:{}", docIds);
            return;
        }
        for (String docId : docIds) {
            if (!docMap.containsKey(docId) || Objects.isNull(docMap.get(docId))) {
                continue;
            }
            refreshDoc(docMap.get(docId), true);
        }
    }

    @Override
    public VectorStore getVectorStoreByBaseId(String baseId) {
        if (StrUtil.isBlank(baseId)) {
            return null;
        }
        BaseVO base = baseService.getById(baseId);
        if (Objects.isNull(base) || StrUtil.isBlank(base.getEmbeddingModelId())) {
            return null;
        }
        ModelVO model = modelService.getById(base.getEmbeddingModelId());
        EmbeddingModelService embeddingModelService = embeddingModelServiceFactory.getEmbeddingModelService(model.getPlatform());
        EmbeddingModel embeddingModel = embeddingModelService.getOrCreateEmbeddingModel(model);
        VectorStoreService vectorStoreService = vectorStoreServiceFactory.getVectorStoreService(VectorDatabaseEnum.MILVUS);
        return vectorStoreService.getOrCreateVectorStore(base.getVectorCollectionName(), embeddingModel);
    }


    private void refreshDoc(DocVO doc, boolean isForced) {
        // 更新文档状态
        docService.updateFileStatusById(doc.getId(), DocStatusEnum.EMB_PROCESSING);
        try {
            VectorStore vectorStore = getVectorStoreByBaseId(doc.getBaseId());
            Assert.nonNull(vectorStore);
            if (isForced) {
                log.info("forced refreshDoc.docId:{}", doc.getId());
                List<ChunkEntity> chunks = chunkMapper.selectListByDocId(doc.getId());
                if (CollUtil.isEmpty(chunks)) {
                    return;
                }
                vectorStore.delete(List.of(doc.getId()));
                chunkMapper.update(Wrappers.<ChunkEntity>lambdaUpdate()
                        .set(ChunkEntity::getEmbStatus, SliceEmbStatus.PENDING)
                        .in(ChunkEntity::getId, CollUtils.convertList(chunks, ChunkEntity::getId))
                );
            }

            while (true) {
                PageDTO<ChunkEntity> slicePage = chunkMapper.selectPage(1, 10, Wrappers.<ChunkEntity>lambdaQuery()
                        .eq(ChunkEntity::getDocId, doc.getId())
                        .in(ChunkEntity::getEmbStatus, List.of(SliceEmbStatus.PENDING, SliceEmbStatus.FAILED))
                );
                if (Objects.isNull(slicePage) || CollUtil.isEmpty(slicePage.getRecords())) {
                    break;
                }
                List<ChunkEntity> records = slicePage.getRecords();
                List<Document> documentList = records.stream().map(item ->
                        new Document(item.getId(), item.getText(), Map.of(KnowledgeConstant.BASE_ID, item.getBaseId(), KnowledgeConstant.DOC_ID, item.getDocId()))
                ).toList();
                vectorStore.add(documentList);

                records.forEach(record -> {
                    record.setEmbStatus(SliceEmbStatus.COMPLETED);
                });
                chunkMapper.updateById(records);
            }
        } catch (Exception e) {
            log.error("refreshVectorDataByDocId error", e);
            docService.updateFileStatusById(doc.getId(), DocStatusEnum.EMB_FAILED);
            return;
        }
        // 更新文档状态
        docService.updateFileStatusById(doc.getId(), DocStatusEnum.EMB_COMPLETED);
    }
}
