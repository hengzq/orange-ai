package cn.hengzq.orange.ai.core.biz.knowledge.converter;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeDocVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddDocumentAndSliceParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddKnowledgeBaseParam;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.KnowledgeDocEntity;
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
public interface KnowledgeDocConverter extends Converter {

    KnowledgeDocConverter INSTANCE = Mappers.getMapper(KnowledgeDocConverter.class);

    KnowledgeDocEntity toEntity(KnowledgeDocVO vo);

    KnowledgeDocEntity toEntity(AddKnowledgeBaseParam request);

    KnowledgeDocVO toVO(KnowledgeDocEntity entity);

    List<KnowledgeDocVO> toListVO(List<KnowledgeDocEntity> entityList);

//    @Mapping(source = "entity.id", target = "id")
//    @Mapping(source = "param.description", target = "description")
//    KnowledgeDocumentEntity toUpdateEntity(KnowledgeDocumentEntity entity, UpdateKnowledgeBaseParam param);

    PageDTO<KnowledgeDocVO> toPage(PageDTO<KnowledgeDocEntity> page);


    @Mapping(source = "baseId", target = "baseId")
    @Mapping(source = "document.fileInfo.fileName", target = "fileName")
    @Mapping(source = "document.fileInfo.filePath", target = "filePath")
    @Mapping(source = "document.fileInfo.fileSize", target = "fileSize")
    KnowledgeDocEntity toEntity(String baseId, AddDocumentAndSliceParam.DocumentInfo document);
}
