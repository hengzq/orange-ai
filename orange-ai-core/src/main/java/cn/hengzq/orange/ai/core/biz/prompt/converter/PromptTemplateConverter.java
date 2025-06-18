package cn.hengzq.orange.ai.core.biz.prompt.converter;


import cn.hengzq.orange.ai.common.biz.prompt.vo.PromptTemplateVO;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.AddPromptTemplateParam;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.UpdatePromptTemplateParam;
import cn.hengzq.orange.ai.core.biz.prompt.entity.PromptTemplateEntity;
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
public interface PromptTemplateConverter extends Converter {

    PromptTemplateConverter INSTANCE = Mappers.getMapper(PromptTemplateConverter.class);

    PromptTemplateEntity toEntity(PromptTemplateVO vo);

    PromptTemplateEntity toEntity(AddPromptTemplateParam param);

    PromptTemplateVO toVO(PromptTemplateEntity entity);

    List<PromptTemplateVO> toListVO(List<PromptTemplateEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "param.name", target = "name")
    @Mapping(source = "param.templateType", target = "templateType")
    @Mapping(source = "param.templateContent", target = "templateContent")
    PromptTemplateEntity toUpdateEntity(PromptTemplateEntity entity, UpdatePromptTemplateParam param);

    PageDTO<PromptTemplateVO> toPage(PageDTO<PromptTemplateEntity> page);
}
