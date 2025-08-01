package cn.hengzq.orange.ai.core.biz.knowledge.converter;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.DocVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddDocAndChunkParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddKnowledgeBaseParam;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.DocEntity;
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
public interface DocConverter extends Converter {

    DocConverter INSTANCE = Mappers.getMapper(DocConverter.class);

    DocEntity toEntity(DocVO vo);

    DocEntity toEntity(AddKnowledgeBaseParam request);

    DocVO toVO(DocEntity entity);

    List<DocVO> toListVO(List<DocEntity> entityList);

//    @Mapping(source = "entity.id", target = "id")
//    @Mapping(source = "param.description", target = "description")
//    KnowledgeDocumentEntity toUpdateEntity(KnowledgeDocumentEntity entity, UpdateKnowledgeBaseParam param);

    PageDTO<DocVO> toPage(PageDTO<DocEntity> page);


    @Mapping(source = "baseId", target = "baseId")
    @Mapping(source = "document.fileInfo.fileName", target = "fileName")
    @Mapping(source = "document.fileInfo.filePath", target = "filePath")
    @Mapping(source = "document.fileInfo.fileSize", target = "fileSize")
    DocEntity toEntity(String baseId, AddDocAndChunkParam.DocumentInfo document);
}
