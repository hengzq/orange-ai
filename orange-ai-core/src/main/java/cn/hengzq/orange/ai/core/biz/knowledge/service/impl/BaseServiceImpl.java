package cn.hengzq.orange.ai.core.biz.knowledge.service.impl;

import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import cn.hengzq.orange.ai.common.biz.knowledge.constant.KnowledgeConstant;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.BaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBaseListParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBasePageParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import cn.hengzq.orange.ai.core.biz.embedding.service.EmbeddingModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.knowledge.converter.BaseConverter;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.BaseEntity;
import cn.hengzq.orange.ai.core.biz.knowledge.mapper.BaseMapper;
import cn.hengzq.orange.ai.core.biz.knowledge.service.BaseService;
import cn.hengzq.orange.ai.core.biz.knowledge.service.DocService;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.ai.core.biz.vectorstore.service.VectorStoreServiceFactory;
import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.common.util.CollUtils;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
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
public class BaseServiceImpl implements BaseService {

    private final BaseMapper baseMapper;

    private final ModelService modelService;

    private final VectorStoreServiceFactory vectorStoreServiceFactory;

    private final EmbeddingModelServiceFactory embeddingModelServiceFactory;

    private final DocService docService;

//    private final KnowledgeDocSliceService knowledgeDocSliceService;

    @Override
    public Boolean removeById(String id) {
        BaseEntity entity = baseMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return Boolean.TRUE;
        }
        baseMapper.deleteById(id);
        // 删除知识库关联文档
        docService.deleteByBaseId(id);
        // 删除知识库关联切片
//        knowledgeDocSliceService.deleteByBaseId(id);
        return Boolean.TRUE;
    }

    @Override
    public String add(AddKnowledgeBaseParam param) {
        BaseEntity entity = BaseConverter.INSTANCE.toEntity(param);
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
        baseMapper.insertOne(entity);
        return null;
    }

    @Override
    public Boolean updateById(String id, UpdateKnowledgeBaseParam request) {
        BaseEntity entity = baseMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity = BaseConverter.INSTANCE.toUpdateEntity(entity, request);
        return baseMapper.updateOneById(entity);
    }

    @Override
    public Boolean updateEnabledById(String id, boolean enabled) {
        BaseEntity entity = baseMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity.setEnabled(enabled);
        return baseMapper.updateOneById(entity);
    }

    @Override
    public BaseVO getById(String id) {
        return BaseConverter.INSTANCE.toVO(baseMapper.selectById(id));
    }

    @Override
    public List<BaseVO> list(KnowledgeBaseListParam param) {
        List<BaseEntity> entityList = baseMapper.selectList(
                CommonWrappers.<BaseEntity>lambdaQuery()
                        .eqIfPresent(BaseEntity::getName, param.getName())
                        .eqIfPresent(BaseEntity::isEnabled, param.getEnabled())
                        .likeIfPresent(BaseEntity::getName, param.getNameLike())
                        .in(CollUtil.isNotEmpty(param.getIds()), BaseEntity::getId, param.getIds())
        );
        return assembleList(BaseConverter.INSTANCE.toListVO(entityList));
    }

    /**
     * 组装知识库列表。
     *
     * @param listVO 需要被组装的知识库列表。
     * @return 如果输入的listVO为空，直接返回；否则，将每个知识库项的嵌入模型ID转换为对应的模型对象，并设置回知识库项中，最后返回处理后的列表。
     */
    private List<BaseVO> assembleList(List<BaseVO> listVO) {
        if (CollUtil.isEmpty(listVO)) {
            return listVO;
        }
        List<String> modelIds = CollUtils.convertList(listVO, BaseVO::getEmbeddingModelId);
        Map<String, ModelVO> modelMap = modelService.mapModelByIds(modelIds);
        listVO.forEach(item -> {
            if (modelMap.containsKey(item.getEmbeddingModelId())) {
                item.setEmbeddingModel(modelMap.get(item.getEmbeddingModelId()));
            }
        });
        return listVO;
    }

    @Override
    public PageDTO<BaseVO> page(KnowledgeBasePageParam param) {
        PageDTO<BaseEntity> page = baseMapper.selectPage(param, CommonWrappers.<BaseEntity>lambdaQuery()
                .eqIfPresent(BaseEntity::getEmbeddingModelId, param.getEmbeddingModelId())
                .likeIfPresent(BaseEntity::getName, param.getName())
                .orderByDesc(BaseEntity::getSort, cn.hengzq.orange.mybatis.entity.BaseEntity::getCreatedAt));
        return BaseConverter.INSTANCE.toPage(page);
    }

    @Override
    public Map<String, BaseVO> mapKnowledgeBaseByIds(List<String> baseIds) {
        if (CollUtil.isEmpty(baseIds)) {
            return Map.of();
        }
        List<BaseEntity> entityList = baseMapper.selectList(CommonWrappers.<BaseEntity>lambdaQuery().in(BaseEntity::getId, baseIds));
        List<BaseVO> baseList = BaseConverter.INSTANCE.toListVO(entityList);
        return CollUtils.convertMap(baseList, BaseVO::getId, baseVO -> baseVO);
    }

}
