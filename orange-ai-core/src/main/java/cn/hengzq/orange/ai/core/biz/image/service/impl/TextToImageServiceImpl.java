package cn.hengzq.orange.ai.core.biz.image.service.impl;

import cn.hengzq.orange.ai.common.biz.image.dto.ImageModelGenerateParam;
import cn.hengzq.orange.ai.common.biz.image.service.ImageModelService;
import cn.hengzq.orange.ai.common.biz.image.vo.TextToImageVO;
import cn.hengzq.orange.ai.common.biz.image.vo.param.GenerateImageParam;
import cn.hengzq.orange.ai.common.biz.image.vo.param.TextToImageListParam;
import cn.hengzq.orange.ai.common.biz.image.vo.param.TextToImagePageParam;
import cn.hengzq.orange.ai.common.biz.model.dto.ModelResponse;
import cn.hengzq.orange.ai.core.biz.image.converter.TextToImageConverter;
import cn.hengzq.orange.ai.core.biz.image.entity.TextToImageEntity;
import cn.hengzq.orange.ai.core.biz.image.mapper.TextToImageMapper;
import cn.hengzq.orange.ai.core.biz.image.service.ImageModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.image.service.TextToImageService;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.mybatis.entity.BaseEntity;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hengzq.orange.system.api.biz.storage.StorageObjectApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class TextToImageServiceImpl implements TextToImageService {

    private final ImageModelServiceFactory imageModelServiceFactory;

    private final TextToImageMapper textToImageMapper;

    private final StorageObjectApi storageObjectApi;

    private final ModelService modelService;

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
        ModelResponse model = modelService.getById(param.getModelId());
        if (Objects.isNull(model)) {
            return null;
        }

        ImageModelService imageModelService = imageModelServiceFactory.getImageModelService(param.getPlatform());
        ImageResponse response = imageModelService.textToImage(ImageModelGenerateParam.builder()
                .model(model)
                .prompt(param.getPrompt())
                .height(param.getHeight())
                .width(param.getWidth())
                .quantity(param.getQuantity())
                .build());
        List<ImageGeneration> results = response.getResults();

        log.error("results:{}", results);
//        List<StorageObjectUploadParam> storageObjectUploadParams = results.stream().filter(Objects::nonNull).map(result -> StorageObjectUploadParam.builder()
//                .originalName(param.getPlatform() + "_" + param.getModelCode() + "." + ImgUtil.IMAGE_TYPE_PNG)
//                // base64 解析
//                .content(Base64.decode(result.getOutput().getB64Json()))
//                .build()).toList();
//        List<StorageObjectVO> storageObjectVOList = storageObjectApi.batchUpload(storageObjectUploadParams);
//         if (CollUtil.isEmpty(storageObjectVOList)) {
//             return null;
//         }
//        TextToImageEntity entity = TextToImageConverter.INSTANCE.toEntity(param, storageObjectVOList);
//        entity.setUserId(GlobalContextHelper.getUserId());
//        textToImageMapper.insertOne(entity);
//        return TextToImageConverter.INSTANCE.toVO(entity);
        return null;
    }


}
