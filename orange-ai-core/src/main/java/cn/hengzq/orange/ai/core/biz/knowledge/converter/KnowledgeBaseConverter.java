package cn.hengzq.orange.ai.core.biz.knowledge.converter;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeBaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateKnowledgeBaseParam;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.KnowledgeBaseEntity;
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
public interface KnowledgeBaseConverter extends Converter {

    KnowledgeBaseConverter INSTANCE = Mappers.getMapper(KnowledgeBaseConverter.class);

    KnowledgeBaseEntity toEntity(KnowledgeBaseVO vo);

    KnowledgeBaseEntity toEntity(AddKnowledgeBaseParam request);

    KnowledgeBaseVO toVO(KnowledgeBaseEntity entity);

    List<KnowledgeBaseVO> toListVO(List<KnowledgeBaseEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "param.name", target = "name")
    @Mapping(source = "param.sort", target = "sort")
    @Mapping(source = "param.embeddingModelId", target = "embeddingModelId")
    @Mapping(source = "param.enabled", target = "enabled")
    @Mapping(source = "param.description", target = "description")
    KnowledgeBaseEntity toUpdateEntity(KnowledgeBaseEntity entity, UpdateKnowledgeBaseParam param);

    PageDTO<KnowledgeBaseVO> toPage(PageDTO<KnowledgeBaseEntity> page);
}
