package cn.hengzq.orange.ai.core.biz.knowledge.converter;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.BaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateKnowledgeBaseParam;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.BaseEntity;
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
public interface BaseConverter extends Converter {

    BaseConverter INSTANCE = Mappers.getMapper(BaseConverter.class);

    BaseEntity toEntity(BaseVO vo);

    BaseEntity toEntity(AddKnowledgeBaseParam request);

    BaseVO toVO(BaseEntity entity);

    List<BaseVO> toListVO(List<BaseEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "param.name", target = "name")
    @Mapping(source = "param.sort", target = "sort")
    @Mapping(source = "param.embeddingModelId", target = "embeddingModelId")
    @Mapping(source = "param.enabled", target = "enabled")
    @Mapping(source = "param.description", target = "description")
    BaseEntity toUpdateEntity(BaseEntity entity, UpdateKnowledgeBaseParam param);

    PageDTO<BaseVO> toPage(PageDTO<BaseEntity> page);
}
