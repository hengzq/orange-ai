package cn.hengzq.orange.ai.core.biz.app.converter;


import cn.hengzq.orange.ai.common.biz.app.dto.AppVersionVO;
import cn.hengzq.orange.ai.common.biz.app.dto.request.AddAppVersionParam;
import cn.hengzq.orange.ai.common.biz.app.dto.request.AppUpdateRequest;
import cn.hengzq.orange.ai.common.biz.app.dto.request.UpdateAppVersionParam;
import cn.hengzq.orange.ai.core.biz.app.entity.AppVersionEntity;
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
public interface AppVersionConverter extends Converter {

    AppVersionConverter INSTANCE = Mappers.getMapper(AppVersionConverter.class);

    AppVersionVO toVO(AppVersionEntity entity);

    AppVersionEntity toEntity(AddAppVersionParam param);

    List<AppVersionVO> toListVO(List<AppVersionEntity> entityList);

    UpdateAppVersionParam toUpdate(AppUpdateRequest param);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "param.name", target = "name")
    @Mapping(source = "param.systemPrompt", target = "systemPrompt")
    @Mapping(source = "param.description", target = "description")
    @Mapping(source = "param.modelId", target = "modelId")
    @Mapping(source = "param.modelConfig", target = "modelConfig")
    @Mapping(source = "param.baseIds", target = "baseIds")
    @Mapping(source = "param.baseConfig", target = "baseConfig")
    @Mapping(source = "param.mcpIds", target = "mcpIds")
    AppVersionEntity toUpdate(AppVersionEntity entity, UpdateAppVersionParam param);

    PageDTO<AppVersionVO> toPage(PageDTO<AppVersionEntity> page);
}
