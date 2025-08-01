package cn.hengzq.orange.ai.core.biz.knowledge.converter;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.ChunkVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddDocSliceParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateDocSliceParam;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.ChunkEntity;
import cn.hengzq.orange.common.converter.Converter;
import cn.hengzq.orange.common.dto.PageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hengzq
 */
@Mapper
public interface ChunkConverter extends Converter {

    ChunkConverter INSTANCE = Mappers.getMapper(ChunkConverter.class);

    ChunkEntity toEntity(AddDocSliceParam vo);

    ChunkVO toVO(ChunkEntity entity);

    List<ChunkVO> toListVO(List<ChunkEntity> entityList);

    //    default List<ChunkEntity> toEntityList(String baseId, String docId, List<ChunkVO> sliceList) {
//        return sliceList.stream()
//                .map(item -> toEntity(baseId, docId, item))
//                .collect(Collectors.toList());
//    }
    List<ChunkEntity> toEntityList(List<ChunkVO> chunks);

    @Mapping(source = "docId", target = "docId")
    @Mapping(source = "baseId", target = "baseId")
    @Mapping(source = "item.text", target = "text")
    ChunkEntity toEntity(String baseId, String docId, ChunkVO item);

    @Mapping(source = "entity.baseId", target = "baseId")
    @Mapping(source = "entity.docId", target = "docId")
    @Mapping(source = "param.text", target = "text")
    ChunkEntity toUpdateEntity(ChunkEntity entity, UpdateDocSliceParam param);

    PageDTO<ChunkVO> toPage(PageDTO<ChunkEntity> page);

    List<ChunkVO> documentListToListVO(List<Document> documents);
}
