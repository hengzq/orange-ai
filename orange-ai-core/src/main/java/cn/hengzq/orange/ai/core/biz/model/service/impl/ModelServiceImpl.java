package cn.hengzq.orange.ai.core.biz.model.service.impl;

import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.ai.core.biz.model.converter.ModelConverter;
import cn.hengzq.orange.ai.core.biz.model.entity.ModelEntity;
import cn.hengzq.orange.ai.core.biz.model.mapper.ModelMapper;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.ai.common.exception.ModelErrorCode;
import cn.hengzq.orange.ai.common.vo.model.ModelVO;
import cn.hengzq.orange.ai.common.vo.model.param.AddModelParam;
import cn.hengzq.orange.ai.common.vo.model.param.ModelListParam;
import cn.hengzq.orange.ai.common.vo.model.param.ModelPageParam;
import cn.hengzq.orange.ai.common.vo.model.param.UpdateModelParam;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Boolean removeById(Long id) {
        ModelEntity entity = modelMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return Boolean.TRUE;
        }
        modelMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Long add(AddModelParam request) {
        ModelEntity entity = ModelConverter.INSTANCE.toEntity(request);
        return modelMapper.insertOne(entity);
    }

    @Override
    public Boolean updateById(Long id, UpdateModelParam request) {
        ModelEntity entity = modelMapper.selectById(id);
        Assert.nonNull(entity, ModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity = ModelConverter.INSTANCE.toUpdateEntity(entity, request);
        return modelMapper.updateOneById(entity);
    }

    @Override
    public ModelVO getById(Long id) {
        return ModelConverter.INSTANCE.toVO(modelMapper.selectById(id));
    }

    @Override
    public List<ModelVO> list(ModelListParam param) {
        List<ModelEntity> entityList = modelMapper.selectList(
                CommonWrappers.<ModelEntity>lambdaQuery()
                        .eqIfPresent(ModelEntity::getPlatform, param.getPlatform())
                        .eqIfPresent(ModelEntity::getType, param.getType())
                        .eqIfPresent(ModelEntity::getName, param.getName())
                        .likeIfPresent(ModelEntity::getName, param.getNameLike())
                        .eqIfPresent(ModelEntity::isEnabled, param.getEnabled())
        );
        return ModelConverter.INSTANCE.toListV0(entityList);
    }

    @Override
    public PageDTO<ModelVO> page(ModelPageParam param) {
        PageDTO<ModelEntity> page = modelMapper.selectPage(param, CommonWrappers.<ModelEntity>lambdaQuery()
                .eqIfPresent(ModelEntity::getPlatform, param.getPlatform())
                .eqIfPresent(ModelEntity::getType, param.getType())
                .eqIfPresent(ModelEntity::getName, param.getName())
                .likeIfPresent(ModelEntity::getName, param.getNameLike())
                .orderByDesc(ModelEntity::getSort));
        return ModelConverter.INSTANCE.toPage(page);
    }


}
