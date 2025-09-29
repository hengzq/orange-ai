package cn.hengzq.orange.ai.core.biz.app.converter;


import cn.hengzq.orange.ai.common.biz.app.dto.AppVO;
import cn.hengzq.orange.ai.common.biz.app.dto.request.AppCreateRequest;
import cn.hengzq.orange.ai.common.biz.app.dto.request.AppUpdateRequest;
import cn.hengzq.orange.ai.core.biz.app.entity.AppEntity;
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
public interface AppConverter extends Converter {

    AppConverter INSTANCE = Mappers.getMapper(AppConverter.class);

    AppEntity toEntity(AppVO modelVO);

    AppEntity toEntity(AppCreateRequest request);

    AppVO toVO(AppEntity entity);

    List<AppVO> toListVO(List<AppEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    AppEntity toUpdateEntity(AppEntity entity, AppUpdateRequest param);

    PageDTO<AppVO> toPage(PageDTO<AppEntity> page);
}
