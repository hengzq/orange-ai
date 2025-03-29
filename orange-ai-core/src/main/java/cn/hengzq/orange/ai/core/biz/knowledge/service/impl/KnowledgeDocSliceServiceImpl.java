package cn.hengzq.orange.ai.core.biz.knowledge.service.impl;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.KnowledgeConstant;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeDocSliceVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.*;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.core.biz.knowledge.converter.KnowledgeDocSliceConverter;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.KnowledgeDocSliceEntity;
import cn.hengzq.orange.ai.core.biz.knowledge.mapper.KnowledgeDocSliceMapper;
import cn.hengzq.orange.ai.core.biz.knowledge.service.KnowledgeBaseService;
import cn.hengzq.orange.ai.core.biz.knowledge.service.KnowledgeDocSliceService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class KnowledgeDocSliceServiceImpl implements KnowledgeDocSliceService {

    private final KnowledgeDocSliceMapper knowledgeDocSliceMapper;

    private final KnowledgeBaseService knowledgeBaseService;

    @Override
    public Boolean deleteById(String id) {
        KnowledgeDocSliceEntity entity = knowledgeDocSliceMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return Boolean.TRUE;
        }
        // 先删除向量数据库中数据
        VectorStore vectorStore = knowledgeBaseService.getVectorStoreById(entity.getBaseId());
        vectorStore.delete(List.of(entity.getId()));
        // 删除数据库中切片
        knowledgeDocSliceMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteByDocId(String docId) {
        List<String> sliceIds = knowledgeDocSliceMapper.selectListIdByDocId(docId);
        if (CollUtil.isEmpty(sliceIds)) {
            return Boolean.TRUE;
        }
        KnowledgeDocSliceEntity entity = knowledgeDocSliceMapper.selectById(sliceIds.get(0));
        VectorStore vectorStore = knowledgeBaseService.getVectorStoreById(entity.getBaseId());
        if (Objects.isNull(vectorStore)) {
            log.error("vectorStore is null. delete doc[{}] is failed.", docId);
            return Boolean.FALSE;
        }
        // 删除文档向量数据
        vectorStore.delete(sliceIds);
        knowledgeDocSliceMapper.deleteByIds(sliceIds);
        return Boolean.TRUE;
    }

    @Override
    public String add(AddDocSliceParam param) {
        KnowledgeDocSliceEntity entity = KnowledgeDocSliceConverter.INSTANCE.toEntity(param);
        VectorStore vectorStore = knowledgeBaseService.getVectorStoreById(entity.getBaseId());

        Document document = new Document(entity.getId(), entity.getContent(),
                Map.of(KnowledgeConstant.BASE_ID, entity.getBaseId(), KnowledgeConstant.DOC_ID, entity.getDocId()));
        vectorStore.add(List.of(document));

        return knowledgeDocSliceMapper.insertOne(entity);
    }

    @Override
    public Boolean updateById(String id, UpdateDocSliceParam param) {
        KnowledgeDocSliceEntity entity = knowledgeDocSliceMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity = KnowledgeDocSliceConverter.INSTANCE.toUpdateEntity(entity, param);

        // 先删除向量数据库中数据
        VectorStore vectorStore = knowledgeBaseService.getVectorStoreById(entity.getBaseId());
        vectorStore.delete(List.of(entity.getId()));
        // 在插入新的切片
        Document document = new Document(entity.getId(), entity.getContent(),
                Map.of(KnowledgeConstant.BASE_ID, entity.getBaseId(), KnowledgeConstant.DOC_ID, entity.getDocId()));
        vectorStore.add(List.of(document));
        return knowledgeDocSliceMapper.updateOneById(entity);
    }

    @Override
    public KnowledgeDocSliceVO getById(String id) {
        return KnowledgeDocSliceConverter.INSTANCE.toVO(knowledgeDocSliceMapper.selectById(id));
    }

    @Override
    public List<KnowledgeDocSliceVO> list(KnowledgeDocSliceListParam param) {
        List<KnowledgeDocSliceEntity> entityList = knowledgeDocSliceMapper.selectList(CommonWrappers.<KnowledgeDocSliceEntity>lambdaQuery()
                .eqIfPresent(KnowledgeDocSliceEntity::getDocId, param.getDocId())
                .orderByDesc(KnowledgeDocSliceEntity::getCreatedAt)
        );
        return KnowledgeDocSliceConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public PageDTO<KnowledgeDocSliceVO> page(KnowledgeDocSlicePageParam param) {
        PageDTO<KnowledgeDocSliceEntity> page = knowledgeDocSliceMapper.selectPage(param, CommonWrappers.<KnowledgeDocSliceEntity>lambdaQuery()
                .eqIfPresent(KnowledgeDocSliceEntity::getDocId, param.getDocId())
                .orderByDesc(KnowledgeDocSliceEntity::getCreatedAt)
        );
        return KnowledgeDocSliceConverter.INSTANCE.toPage(page);
    }


}
