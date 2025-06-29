package cn.hengzq.orange.ai.core.biz.knowledge.service.impl;

import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import cn.hengzq.orange.ai.common.biz.knowledge.constant.KnowledgeConstant;
import cn.hengzq.orange.ai.common.biz.knowledge.constant.KnowledgeErrorCode;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeBaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBaseListParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBasePageParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import cn.hengzq.orange.ai.core.biz.agent.entity.AgentEntity;
import cn.hengzq.orange.ai.core.biz.agent.mapper.AgentMapper;
import cn.hengzq.orange.ai.core.biz.embedding.service.EmbeddingModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.knowledge.converter.KnowledgeBaseConverter;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.KnowledgeBaseEntity;
import cn.hengzq.orange.ai.core.biz.knowledge.mapper.KnowledgeBaseMapper;
import cn.hengzq.orange.ai.core.biz.knowledge.service.KnowledgeBaseService;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.ai.core.biz.vectorstore.service.VectorStoreServiceFactory;
import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.exception.ServiceException;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.common.util.CollUtils;
import cn.hengzq.orange.mybatis.entity.BaseEntity;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
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
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final KnowledgeBaseMapper knowledgeBaseMapper;

    private final ModelService modelService;

    private final VectorStoreServiceFactory vectorStoreServiceFactory;

    private final EmbeddingModelServiceFactory embeddingModelServiceFactory;

    private final AgentMapper agentMapper;

    @Override
    public Boolean removeById(String id) {
        KnowledgeBaseEntity entity = knowledgeBaseMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return Boolean.TRUE;
        }
        // 检查知识库是否被使用
        List<AgentEntity> agentEntityList = agentMapper.selectList(Wrappers.<AgentEntity>lambdaQuery().like(AgentEntity::getBaseIds, id));
        Assert.isEmpty(agentEntityList, KnowledgeErrorCode.KNOWLEDGE_BASE_DELETE_FAILED);
        knowledgeBaseMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public String add(AddKnowledgeBaseParam param) {
        KnowledgeBaseEntity entity = KnowledgeBaseConverter.INSTANCE.toEntity(param);
        // 查询知识库使用的向量模型
        ModelVO model = modelService.getById(param.getEmbeddingModelId());
        String baseId = IdUtil.getSnowflakeNextIdStr();

        EmbeddingModelService embeddingModelService = embeddingModelServiceFactory.getEmbeddingModelService(model.getPlatform());
        EmbeddingModel embeddingModel = embeddingModelService.getOrCreateEmbeddingModel(model);

        String vectorCollectionName = KnowledgeConstant.VECTOR_COLLECTION_PREFIX + RandomUtil.randomString(6).toLowerCase();
        VectorStoreService vectorStoreService = vectorStoreServiceFactory.getVectorStoreService(VectorDatabaseEnum.MILVUS);
        VectorStore vectorStore = vectorStoreService.createVectorStore(vectorCollectionName, embeddingModel);
        Assert.nonNull(vectorStore, GlobalErrorCodeConstant.GLOBAL_BAD_REQUEST);
        entity.setVectorCollectionName(vectorCollectionName);
        entity.setId(baseId);
        knowledgeBaseMapper.insertOne(entity);
        return null;
    }

    @Override
    public Boolean updateById(String id, UpdateKnowledgeBaseParam request) {
        KnowledgeBaseEntity entity = knowledgeBaseMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity = KnowledgeBaseConverter.INSTANCE.toUpdateEntity(entity, request);
        return knowledgeBaseMapper.updateOneById(entity);
    }

    @Override
    public KnowledgeBaseVO getById(String id) {
        return KnowledgeBaseConverter.INSTANCE.toVO(knowledgeBaseMapper.selectById(id));
    }

    @Override
    public List<KnowledgeBaseVO> list(KnowledgeBaseListParam param) {
        List<KnowledgeBaseEntity> entityList = knowledgeBaseMapper.selectList(
                CommonWrappers.<KnowledgeBaseEntity>lambdaQuery()
                        .eqIfPresent(KnowledgeBaseEntity::getName, param.getName())
                        .eqIfPresent(KnowledgeBaseEntity::isEnabled, param.getEnabled())
                        .likeIfPresent(KnowledgeBaseEntity::getName, param.getNameLike())
                        .in(CollUtil.isNotEmpty(param.getIds()), KnowledgeBaseEntity::getId, param.getIds())
        );
        return assembleList(KnowledgeBaseConverter.INSTANCE.toListVO(entityList));
    }

    /**
     * 组装知识库列表。
     *
     * @param listVO 需要被组装的知识库列表。
     * @return 如果输入的listVO为空，直接返回；否则，将每个知识库项的嵌入模型ID转换为对应的模型对象，并设置回知识库项中，最后返回处理后的列表。
     */
    private List<KnowledgeBaseVO> assembleList(List<KnowledgeBaseVO> listVO) {
        if (CollUtil.isEmpty(listVO)) {
            return listVO;
        }
        List<String> modelIds = CollUtils.convertList(listVO, KnowledgeBaseVO::getEmbeddingModelId);
        Map<String, ModelVO> modelMap = modelService.mapModelByIds(modelIds);
        listVO.forEach(item -> {
            if (modelMap.containsKey(item.getEmbeddingModelId())) {
                item.setEmbeddingModel(modelMap.get(item.getEmbeddingModelId()));
            }
        });
        return listVO;
    }

    @Override
    public PageDTO<KnowledgeBaseVO> page(KnowledgeBasePageParam param) {
        PageDTO<KnowledgeBaseEntity> page = knowledgeBaseMapper.selectPage(param, CommonWrappers.<KnowledgeBaseEntity>lambdaQuery()
                .eqIfPresent(KnowledgeBaseEntity::getEmbeddingModelId, param.getEmbeddingModelId())
                .likeIfPresent(KnowledgeBaseEntity::getName, param.getName())
                .orderByDesc(KnowledgeBaseEntity::getSort, BaseEntity::getCreatedAt));
        return KnowledgeBaseConverter.INSTANCE.toPage(page);
    }

    @Override
    public Map<String, KnowledgeBaseVO> mapKnowledgeBaseByIds(List<String> baseIds) {
        if (CollUtil.isEmpty(baseIds)) {
            return Map.of();
        }
        List<KnowledgeBaseEntity> entityList = knowledgeBaseMapper.selectList(CommonWrappers.<KnowledgeBaseEntity>lambdaQuery().in(KnowledgeBaseEntity::getId, baseIds));
        List<KnowledgeBaseVO> baseList = KnowledgeBaseConverter.INSTANCE.toListVO(entityList);
        return CollUtils.convertMap(baseList, KnowledgeBaseVO::getId, baseVO -> baseVO);
    }

    @Override
    public VectorStore getVectorStoreById(String id) {
        if (Objects.isNull(id)) {
            return null;
        }
        KnowledgeBaseEntity entity = knowledgeBaseMapper.selectById(id);
        if (Objects.isNull(entity) || StrUtil.isBlank(entity.getEmbeddingModelId())) {
            return null;
        }
        ModelVO model = modelService.getById(entity.getEmbeddingModelId());
        EmbeddingModelService embeddingModelService = embeddingModelServiceFactory.getEmbeddingModelService(model.getPlatform());
        EmbeddingModel embeddingModel = embeddingModelService.getOrCreateEmbeddingModel(model);
        VectorStoreService vectorStoreService = vectorStoreServiceFactory.getVectorStoreService(VectorDatabaseEnum.MILVUS);
        return vectorStoreService.getOrCreateVectorStore(entity.getVectorCollectionName(), embeddingModel);
    }

}
