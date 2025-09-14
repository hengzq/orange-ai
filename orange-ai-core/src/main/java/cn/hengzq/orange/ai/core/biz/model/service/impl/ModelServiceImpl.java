package cn.hengzq.orange.ai.core.biz.model.service.impl;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.model.constant.ModelConstant;
import cn.hengzq.orange.ai.common.biz.model.dto.ModelResponse;
import cn.hengzq.orange.ai.common.biz.model.dto.param.ModelCreateRequest;
import cn.hengzq.orange.ai.common.biz.model.dto.param.ModelQueryRequest;
import cn.hengzq.orange.ai.common.biz.model.dto.param.ModelPageRequest;
import cn.hengzq.orange.ai.common.biz.model.dto.param.ModelUpdateRequest;
import cn.hengzq.orange.ai.common.constant.ModelTypeEnum;
import cn.hengzq.orange.ai.common.constant.RedisKeys;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.embedding.service.EmbeddingModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.model.converter.ModelConverter;
import cn.hengzq.orange.ai.core.biz.model.entity.ModelEntity;
import cn.hengzq.orange.ai.core.biz.model.mapper.ModelMapper;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.exception.ServiceException;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.common.util.CollUtils;
import cn.hengzq.orange.mybatis.entity.BaseEntity;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ChatModelServiceFactory chatModelServiceFactory;

    private final EmbeddingModelServiceFactory embeddingModelServiceFactory;

    private final ModelMapper modelMapper;


    @Override
    public String createModel(ModelCreateRequest request) {
        if (StrUtil.isNotBlank(request.getApiKey())) {
            request.setApiKey(SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).encryptBase64(request.getApiKey()));
        }
        // 模型检测
        if (!checkModel(request)) {
            throw new ServiceException(AIModelErrorCode.MODEL_PARAM_APIKEY_OR_BASEURL_IS_ERROR);
        }

        ModelEntity entity = ModelConverter.INSTANCE.toEntity(request);
        return modelMapper.insertOne(entity);
    }

    private boolean checkModel(ModelCreateRequest request) {
        try {
            if (ModelTypeEnum.CHAT.equals(request.getModelType())) {
                ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(request.getPlatform());
                ChatModel chatModel = chatModelService.getOrCreateChatModel(request.getModelName(), request.getBaseUrl(), request.getApiKey());
                chatModel.call("你好!");
            } else if (ModelTypeEnum.EMBEDDING.equals(request.getModelType())) {
                EmbeddingModelService embeddingModelService = embeddingModelServiceFactory.getEmbeddingModelService(request.getPlatform());
                EmbeddingModel embeddingModel = embeddingModelService.getOrCreateEmbeddingModel(request.getModelName(), request.getBaseUrl(), request.getApiKey());
                embeddingModel.embed("你好");
            }
        } catch (Exception e) {
            log.error("模型检测异常", e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    @CacheEvict(cacheNames = {RedisKeys.MODEL_BASIC_KEY_PREFIX}, key = "#id")
    public void deleteModelById(String id) {
        ModelEntity entity = modelMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return;
        }
        modelMapper.deleteById(id);
    }

    @Override
    @CacheEvict(cacheNames = {RedisKeys.MODEL_BASIC_KEY_PREFIX}, key = "#id")
    public void updateModelById(String id, ModelUpdateRequest request) {
        ModelEntity entity = modelMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity = ModelConverter.INSTANCE.toUpdateEntity(entity, request);
        if (StrUtil.isNotBlank(entity.getApiKey())) {
            entity.setApiKey(SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).encryptBase64(entity.getApiKey()));
        }
        modelMapper.updateOneById(entity);
    }

    @Override
    @CacheEvict(cacheNames = {RedisKeys.MODEL_BASIC_KEY_PREFIX}, key = "#id")
    public void updateEnabledById(String id, boolean enabled) {
        ModelEntity entity = modelMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity.setEnabled(enabled);
        modelMapper.updateOneById(entity);
    }

    @Override
    @Cacheable(cacheNames = {RedisKeys.MODEL_BASIC_KEY_PREFIX}, key = "#id")
    public Optional<ModelResponse> getModelById(String id) {
        if (StrUtil.isBlank(id)) return Optional.empty();
        return Optional.ofNullable(ModelConverter.INSTANCE.toVO(modelMapper.selectById(id)));
    }

    @Override
    public ModelResponse getById(String id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return ModelConverter.INSTANCE.toVO(modelMapper.selectById(id));
    }

    @Override
    public List<ModelResponse> list(ModelQueryRequest param) {
        List<ModelEntity> entityList = modelMapper.selectList(
                CommonWrappers.<ModelEntity>lambdaQuery()
                        .eqIfPresent(ModelEntity::getPlatform, param.getPlatform())
                        .eqIfPresent(ModelEntity::getModelType, param.getModelType())
                        .eqIfPresent(ModelEntity::getName, param.getName())
                        .likeIfPresent(ModelEntity::getName, param.getNameLike())
                        .eqIfPresent(ModelEntity::isEnabled, param.getEnabled())
        );
        return ModelConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public PageDTO<ModelResponse> page(ModelPageRequest param) {
        PageDTO<ModelEntity> page = modelMapper.selectPage(param, CommonWrappers.<ModelEntity>lambdaQuery()
                .eqIfPresent(ModelEntity::getPlatform, param.getPlatform())
                .eqIfPresent(ModelEntity::getModelType, param.getModelType())
                .eqIfPresent(ModelEntity::getName, param.getName())
                .likeIfPresent(ModelEntity::getName, param.getNameLike())
                .orderByDesc(ModelEntity::getSort, BaseEntity::getCreatedAt));
        return ModelConverter.INSTANCE.toPage(page);
    }

    @Override
    public Map<String, ModelResponse> mapModelByIds(List<String> modelIds) {
        if (CollUtil.isEmpty(modelIds)) {
            return Map.of();
        }
        List<ModelEntity> entityList = modelMapper.selectList(CommonWrappers.<ModelEntity>lambdaQuery().in(ModelEntity::getId, modelIds));
        List<ModelResponse> modelList = ModelConverter.INSTANCE.toListVO(entityList);
        return CollUtils.convertMap(modelList, ModelResponse::getId, modelVO -> modelVO);
    }


}
