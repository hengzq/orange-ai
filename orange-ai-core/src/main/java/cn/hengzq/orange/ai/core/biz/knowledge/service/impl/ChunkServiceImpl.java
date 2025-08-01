package cn.hengzq.orange.ai.core.biz.knowledge.service.impl;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.SliceEmbStatus;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.ChunkVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddDocSliceParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeDocSliceListParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeDocSlicePageParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateDocSliceParam;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.core.biz.knowledge.converter.ChunkConverter;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.ChunkEntity;
import cn.hengzq.orange.ai.core.biz.knowledge.mapper.ChunkMapper;
import cn.hengzq.orange.ai.core.biz.knowledge.service.ChunkService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.common.util.CollUtils;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.BatchResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChunkServiceImpl implements ChunkService {

//    private final VectorSyncService vectorSyncService;

    private final ChunkMapper chunkMapper;


    @Override
    public Boolean deleteById(String id) {
        ChunkEntity entity = chunkMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return Boolean.TRUE;
        }
        // 先删除向量数据库中数据
//        VectorStore vectorStore = vectorSyncService.getVectorStoreByBaseId(entity.getBaseId());
//        vectorStore.delete(List.of(entity.getId()));
        // 删除数据库中切片
        chunkMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteByDocId(String docId) {
        List<ChunkEntity> chunks = chunkMapper.selectListByDocId(docId);
        if (CollUtil.isEmpty(chunks)) {
            return Boolean.TRUE;
        }
//        ChunkEntity entity = chunkMapper.selectById(chunkIds.get(0));
//        VectorStore vectorStore = vectorSyncService.getVectorStoreByBaseId(entity.getBaseId());
//        if (Objects.isNull(vectorStore)) {
//            log.error("vectorStore is null. delete doc[{}] is failed.", docId);
//            return Boolean.FALSE;
//        }
//        // 删除文档向量数据
//        vectorStore.delete(sliceIds);
        return chunkMapper.deleteByIds(CollUtils.convertList(chunks, ChunkEntity::getId)) > 0;
    }

    @Override
    public Boolean deleteByBaseId(String baseId) {
        List<ChunkEntity> sliceList = chunkMapper.selectListByBaseId(baseId);
        if (CollUtil.isEmpty(sliceList)) {
            return Boolean.TRUE;
        }

        List<String> sliceIds = CollUtils.convertList(sliceList, ChunkEntity::getId);
        // 删除向量数据
//        VectorStore vectorStore = vectorSyncService.getVectorStoreByBaseId(baseId);
//        if (Objects.isNull(vectorStore)) {
//            log.error("vectorStore is null. delete base[{}] is failed.", baseId);
//            return Boolean.FALSE;
//        }
//        // 删除文档向量数据
//        vectorStore.delete(sliceIds);
        int delete = chunkMapper.deleteByIds(sliceIds);
        return delete > 0;
    }

    @Override
    public String add(AddDocSliceParam param) {
        ChunkEntity entity = ChunkConverter.INSTANCE.toEntity(param);
//        VectorStore vectorStore = vectorSyncService.getVectorStoreByBaseId(entity.getBaseId());
//
//        Document document = new Document(entity.getId(), entity.getText(),
//                Map.of(KnowledgeConstant.BASE_ID, entity.getBaseId(), KnowledgeConstant.DOC_ID, entity.getDocId()));
//        vectorStore.add(List.of(document));

        return chunkMapper.insertOne(entity);
    }

    @Override
    public Boolean batchAdd(List<ChunkVO> chunks) {
        List<ChunkEntity> entityList = ChunkConverter.INSTANCE.toEntityList(chunks);
        if (CollUtil.isEmpty(entityList)) {
            log.warn("batchAdd entityList is null or empty.");
            return Boolean.TRUE;
        }
        entityList.forEach(item -> item.setEmbStatus(SliceEmbStatus.PENDING));
        List<BatchResult> insert = chunkMapper.insert(entityList);
        return !insert.isEmpty();
    }

    @Override
    public Boolean updateById(String id, UpdateDocSliceParam param) {
        ChunkEntity entity = chunkMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity = ChunkConverter.INSTANCE.toUpdateEntity(entity, param);

        // 先删除向量数据库中数据
//        VectorStore vectorStore = vectorSyncService.getVectorStoreByBaseId(entity.getBaseId());
//        vectorStore.delete(List.of(entity.getId()));
        // 在插入新的切片
//        Document document = new Document(entity.getId(), entity.getText(),
//                Map.of(KnowledgeConstant.BASE_ID, entity.getBaseId(), KnowledgeConstant.DOC_ID, entity.getDocId()));
//        vectorStore.add(List.of(document));
        return chunkMapper.updateOneById(entity);
    }

    @Override
    public ChunkVO getById(String id) {
        return ChunkConverter.INSTANCE.toVO(chunkMapper.selectById(id));
    }

    @Override
    public List<ChunkVO> list(KnowledgeDocSliceListParam param) {
        List<ChunkEntity> entityList = chunkMapper.selectList(CommonWrappers.<ChunkEntity>lambdaQuery()
                .eqIfPresent(ChunkEntity::getDocId, param.getDocId())
                .orderByDesc(ChunkEntity::getCreatedAt)
        );
        return ChunkConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public PageDTO<ChunkVO> page(KnowledgeDocSlicePageParam param) {
        PageDTO<ChunkEntity> page = chunkMapper.selectPage(param, CommonWrappers.<ChunkEntity>lambdaQuery()
                .eqIfPresent(ChunkEntity::getDocId, param.getDocId())
                .orderByDesc(ChunkEntity::getCreatedAt)
        );
        return ChunkConverter.INSTANCE.toPage(page);
    }


}
