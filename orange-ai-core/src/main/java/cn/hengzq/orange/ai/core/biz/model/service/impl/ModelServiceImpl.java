package cn.hengzq.orange.ai.core.biz.model.service.impl;

import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.model.constant.ModelConstant;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.model.vo.param.AddModelParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.ModelListParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.ModelPageParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.UpdateModelParam;
import cn.hengzq.orange.ai.core.biz.model.converter.ModelConverter;
import cn.hengzq.orange.ai.core.biz.model.entity.ModelEntity;
import cn.hengzq.orange.ai.core.biz.model.mapper.ModelMapper;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.common.util.CollUtils;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class ModelServiceImpl implements ModelService {


    private final ModelMapper modelMapper;

    @Override
    public Boolean removeById(String id) {
        ModelEntity entity = modelMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return Boolean.TRUE;
        }
        modelMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public String add(AddModelParam request) {
        ModelEntity entity = ModelConverter.INSTANCE.toEntity(request);
        if (StrUtil.isNotBlank(entity.getApiKey())) {
            entity.setApiKey(SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).encryptBase64(entity.getApiKey()));
        }
        return modelMapper.insertOne(entity);
    }

    @Override
    public Boolean updateById(String id, UpdateModelParam request) {
        ModelEntity entity = modelMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity = ModelConverter.INSTANCE.toUpdateEntity(entity, request);
        if (StrUtil.isNotBlank(entity.getApiKey())) {
            entity.setApiKey(SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).encryptBase64(entity.getApiKey()));
        }
        return modelMapper.updateOneById(entity);
    }

    @Override
    public ModelVO getById(String id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return ModelConverter.INSTANCE.toVO(modelMapper.selectById(id));
    }

    @Override
    public List<ModelVO> list(ModelListParam param) {
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
    public PageDTO<ModelVO> page(ModelPageParam param) {
        PageDTO<ModelEntity> page = modelMapper.selectPage(param, CommonWrappers.<ModelEntity>lambdaQuery()
                .eqIfPresent(ModelEntity::getPlatform, param.getPlatform())
                .eqIfPresent(ModelEntity::getModelType, param.getModelType())
                .eqIfPresent(ModelEntity::getName, param.getName())
                .likeIfPresent(ModelEntity::getName, param.getNameLike())
                .orderByDesc(ModelEntity::getSort));
        return ModelConverter.INSTANCE.toPage(page);
    }

    @Override
    public Map<String, ModelVO> mapModelByIds(List<String> modelIds) {
        if (CollUtil.isEmpty(modelIds)) {
            return Map.of();
        }
        List<ModelEntity> entityList = modelMapper.selectList(CommonWrappers.<ModelEntity>lambdaQuery().in(ModelEntity::getId, modelIds));
        List<ModelVO> modelList = ModelConverter.INSTANCE.toListVO(entityList);
        return CollUtils.convertMap(modelList, ModelVO::getId, modelVO -> modelVO);
    }


}
