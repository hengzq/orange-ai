package cn.hengzq.orange.ai.core.image.converter;


import cn.hengzq.orange.common.converter.Converter;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.ai.core.image.entity.TextToImageEntity;
import cn.hengzq.orange.ai.common.vo.image.TextToImageVO;
import cn.hengzq.orange.ai.common.vo.image.param.GenerateImageParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImageResponse;

import java.util.List;

/**
 * @author hengzq
 */
@Mapper
public interface TextToImageConverter extends Converter {

    TextToImageConverter INSTANCE = Mappers.getMapper(TextToImageConverter.class);

    List<TextToImageVO> toListVO(List<TextToImageEntity> entityList);

    PageDTO<TextToImageVO> toPage(PageDTO<TextToImageEntity> page);

    default TextToImageEntity toEntity(GenerateImageParam param, ImageResponse response) {
        TextToImageEntity entity = toEntity(param);
        List<String> urls = response.getResults().stream().map(ImageGeneration::getOutput).map(Image::getUrl).toList();
        entity.setUrls(urls);
        return entity;
    }

    TextToImageEntity toEntity(GenerateImageParam param);

    TextToImageVO toVO(TextToImageEntity entity);
}
