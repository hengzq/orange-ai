package cn.hengzq.orange.ai.core.biz.knowledge.converter;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeDocSliceVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.SliceInfo;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddDocSliceParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateDocSliceParam;
import cn.hengzq.orange.ai.core.biz.knowledge.entity.KnowledgeDocSliceEntity;
import cn.hengzq.orange.common.converter.Converter;
import cn.hengzq.orange.common.dto.PageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hengzq
 */
@Mapper
public interface KnowledgeDocSliceConverter extends Converter {

    KnowledgeDocSliceConverter INSTANCE = Mappers.getMapper(KnowledgeDocSliceConverter.class);

    KnowledgeDocSliceEntity toEntity(AddDocSliceParam vo);

    KnowledgeDocSliceVO toVO(KnowledgeDocSliceEntity entity);

    List<KnowledgeDocSliceVO> toListVO(List<KnowledgeDocSliceEntity> entityList);

    default List<KnowledgeDocSliceEntity> toEntityList(String baseId, String docId, List<SliceInfo> sliceList) {
        return sliceList.stream()
                .map(item -> toEntity(baseId, docId, item))
                .collect(Collectors.toList());
    }

    @Mapping(source = "docId", target = "docId")
    @Mapping(source = "baseId", target = "baseId")
    @Mapping(source = "item.content", target = "content")
    KnowledgeDocSliceEntity toEntity(String baseId, String docId, SliceInfo item);

    @Mapping(source = "entity.baseId", target = "baseId")
    @Mapping(source = "entity.docId", target = "docId")
    @Mapping(source = "param.content", target = "content")
    KnowledgeDocSliceEntity toUpdateEntity(KnowledgeDocSliceEntity entity, UpdateDocSliceParam param);

    PageDTO<KnowledgeDocSliceVO> toPage(PageDTO<KnowledgeDocSliceEntity> page);
}
