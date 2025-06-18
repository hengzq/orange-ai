package cn.hengzq.orange.ai.core.biz.model.converter;


import cn.hengzq.orange.ai.core.biz.model.entity.ModelEntity;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.model.vo.param.AddModelParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.UpdateModelParam;
import cn.hengzq.orange.common.converter.Converter;
import cn.hengzq.orange.common.dto.PageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hengzq
 */
@Mapper
public interface ModelConverter extends Converter {

    ModelConverter INSTANCE = Mappers.getMapper(ModelConverter.class);

    ModelEntity toEntity(ModelVO modelVO);

    ModelEntity toEntity(AddModelParam request);

    ModelVO toVO(ModelEntity entity);

    List<ModelVO> toListVO(List<ModelEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "param.name", target = "name")
    @Mapping(source = "param.modelName", target = "modelName")
    @Mapping(source = "param.baseUrl", target = "baseUrl")
    @Mapping(source = "param.sort", target = "sort")
    @Mapping(source = "param.description", target = "description")
    ModelEntity toUpdateEntity(ModelEntity entity, UpdateModelParam param);

    PageDTO<ModelVO> toPage(PageDTO<ModelEntity> page);
}
