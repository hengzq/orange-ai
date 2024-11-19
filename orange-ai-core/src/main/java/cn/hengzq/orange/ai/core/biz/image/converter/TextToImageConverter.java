package cn.hengzq.orange.ai.core.biz.image.converter;


import cn.hengzq.orange.ai.common.biz.image.vo.TextToImageVO;
import cn.hengzq.orange.ai.common.biz.image.vo.param.GenerateImageParam;
import cn.hengzq.orange.ai.core.biz.image.entity.TextToImageEntity;
import cn.hengzq.orange.common.converter.Converter;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.system.common.biz.storage.vo.StorageObjectVO;
import cn.hutool.core.collection.CollUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hengzq
 */
@Mapper
public interface TextToImageConverter extends Converter {

    TextToImageConverter INSTANCE = Mappers.getMapper(TextToImageConverter.class);

    List<TextToImageVO> toListVO(List<TextToImageEntity> entityList);

    PageDTO<TextToImageVO> toPage(PageDTO<TextToImageEntity> page);

    default TextToImageEntity toEntity(GenerateImageParam param, List<StorageObjectVO> storageObjectVOList) {
        TextToImageEntity entity = toEntity(param);
        if (CollUtil.isNotEmpty(storageObjectVOList)) {
            entity.setUrls(storageObjectVOList.stream().map(StorageObjectVO::getPreviewUrl).toList());
        }
        return entity;
    }

    TextToImageEntity toEntity(GenerateImageParam param);

    TextToImageVO toVO(TextToImageEntity entity);
}
