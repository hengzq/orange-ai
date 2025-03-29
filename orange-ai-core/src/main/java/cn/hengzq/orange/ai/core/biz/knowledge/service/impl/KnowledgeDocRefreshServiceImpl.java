package cn.hengzq.orange.ai.core.biz.knowledge.service.impl;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.FileStatusEnum;
import cn.hengzq.orange.ai.common.biz.knowledge.constant.KnowledgeConstant;
import cn.hengzq.orange.ai.common.biz.knowledge.constant.SliceEmbStatus;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeDocVO;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.KnowledgeDocSliceEntity;
import cn.hengzq.orange.ai.core.biz.knowledge.mapper.KnowledgeDocSliceMapper;
import cn.hengzq.orange.ai.core.biz.knowledge.service.KnowledgeBaseService;
import cn.hengzq.orange.ai.core.biz.knowledge.service.KnowledgeDocRefreshService;
import cn.hengzq.orange.ai.core.biz.knowledge.service.KnowledgeDocService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class KnowledgeDocRefreshServiceImpl implements KnowledgeDocRefreshService {


    private final KnowledgeBaseService knowledgeBaseService;

    private final KnowledgeDocService knowledgeDocService;

    private final KnowledgeDocSliceMapper knowledgeDocSliceMapper;


    @Override
    public void refreshDocByDocIds(List<String> docIds) {
        Map<String, KnowledgeDocVO> docMap = knowledgeDocService.mapKnowledgeDocByIds(docIds);
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
        Map<String, KnowledgeDocVO> docMap = knowledgeDocService.mapKnowledgeDocByIds(docIds);
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


    private void refreshDoc(KnowledgeDocVO doc, boolean isForced) {
        // 更新文档状态
        knowledgeDocService.updateFileStatusById(doc.getId(), FileStatusEnum.EMB_PROCESSING);
        try {
            VectorStore vectorStore = knowledgeBaseService.getVectorStoreById(doc.getBaseId());
            Assert.nonNull(vectorStore);
            if (isForced) {
                log.info("forced refreshDoc.docId:{}", doc.getId());
                List<String> sliceIds = knowledgeDocSliceMapper.selectListIdByDocId(doc.getId());
                if (CollUtil.isEmpty(sliceIds)) {
                    return;
                }
                vectorStore.delete(List.of(doc.getId()));
                knowledgeDocSliceMapper.update(Wrappers.<KnowledgeDocSliceEntity>lambdaUpdate()
                        .set(KnowledgeDocSliceEntity::getEmbStatus, SliceEmbStatus.PENDING)
                        .in(KnowledgeDocSliceEntity::getId, sliceIds)
                );
            }

            while (true) {
                PageDTO<KnowledgeDocSliceEntity> slicePage = knowledgeDocSliceMapper.selectPage(1, 10, Wrappers.<KnowledgeDocSliceEntity>lambdaQuery()
                        .eq(KnowledgeDocSliceEntity::getDocId, doc.getId())
                        .in(KnowledgeDocSliceEntity::getEmbStatus, List.of(SliceEmbStatus.PENDING, SliceEmbStatus.FAILED))
                );
                if (Objects.isNull(slicePage) || CollUtil.isEmpty(slicePage.getRecords())) {
                    break;
                }
                List<KnowledgeDocSliceEntity> records = slicePage.getRecords();
                List<Document> documentList = records.stream().map(item ->
                        new Document(item.getId(), item.getContent(), Map.of(KnowledgeConstant.BASE_ID, item.getBaseId(), KnowledgeConstant.DOC_ID, item.getDocId()))
                ).toList();
                vectorStore.add(documentList);

                records.forEach(record -> {
                    record.setEmbStatus(SliceEmbStatus.COMPLETED);
                });
                knowledgeDocSliceMapper.updateById(records);
            }
        } catch (Exception e) {
            log.error("refreshVectorDataByDocId error", e);
            knowledgeDocService.updateFileStatusById(doc.getId(), FileStatusEnum.EMB_FAILED);
            return;
        }
        // 更新文档状态
        knowledgeDocService.updateFileStatusById(doc.getId(), FileStatusEnum.SUCCESS);
    }
}
