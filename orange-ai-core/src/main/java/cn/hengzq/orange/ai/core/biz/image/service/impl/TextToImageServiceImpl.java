package cn.hengzq.orange.ai.core.biz.image.service.impl;

import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import cn.hengzq.orange.ai.common.service.image.ImageModelService;
import cn.hengzq.orange.ai.core.biz.image.service.ImageModelServiceFactory;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.ai.core.biz.image.converter.TextToImageConverter;
import cn.hengzq.orange.ai.core.biz.image.entity.TextToImageEntity;
import cn.hengzq.orange.ai.core.biz.image.mapper.TextToImageMapper;
import cn.hengzq.orange.ai.core.biz.image.service.TextToImageService;
import cn.hengzq.orange.ai.common.vo.image.TextToImageVO;
import cn.hengzq.orange.ai.common.vo.image.param.GenerateImageParam;
import cn.hengzq.orange.ai.common.vo.image.param.TextToImageListParam;
import cn.hengzq.orange.ai.common.vo.image.param.TextToImagePageParam;
import cn.hengzq.orange.context.GlobalContextHelper;
import cn.hengzq.orange.mybatis.entity.BaseEntity;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class TextToImageServiceImpl implements TextToImageService {

    private final ImageModelServiceFactory imageModelServiceFactory;

    private final TextToImageMapper textToImageMapper;

    @Override
    public PageDTO<TextToImageVO> page(TextToImagePageParam param) {
        PageDTO<TextToImageEntity> page = textToImageMapper.selectPage(param, CommonWrappers.<TextToImageEntity>lambdaQuery()
                .orderByDesc(TextToImageEntity::getCreatedAt));
        return TextToImageConverter.INSTANCE.toPage(page);
    }

    @Override
    public List<TextToImageVO> list(TextToImageListParam param) {
        List<TextToImageEntity> entityList = textToImageMapper.selectList(
                CommonWrappers.<TextToImageEntity>lambdaQuery().orderByDesc(BaseEntity::getCreatedAt)
        );
        return TextToImageConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public TextToImageVO generate(GenerateImageParam param) {
        ImageModelService imageModelService = imageModelServiceFactory.getImageModelService(param.getPlatform());
        ImageResponse response = imageModelService.textToImage(param);
        TextToImageEntity entity = TextToImageConverter.INSTANCE.toEntity(param, response);
        entity.setUserId(GlobalContextHelper.getUserId());
        textToImageMapper.insertOne(entity);
        return TextToImageConverter.INSTANCE.toVO(entity);
    }

}
