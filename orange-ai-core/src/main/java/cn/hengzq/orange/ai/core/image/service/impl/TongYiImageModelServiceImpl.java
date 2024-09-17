package cn.hengzq.orange.ai.core.image.service.impl;

import cn.hengzq.orange.ai.core.image.service.ImageModelService;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.vo.image.param.GenerateImageParam;
import com.alibaba.cloud.ai.tongyi.image.TongYiImagesModel;
import com.alibaba.cloud.ai.tongyi.image.TongYiImagesOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TongYiImageModelServiceImpl implements ImageModelService {

    private final TongYiImagesModel imageModel;


    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.TONGYI;
    }

    @Override
    public ImageResponse textToImage(GenerateImageParam param) {
        ImagePrompt prompt = new ImagePrompt(param.getPrompt(), TongYiImagesOptions.builder()
                .withN(param.getQuantity()) // 生成图片数量
                .withModel(param.getModelCode())
                .withWidth(param.getWidth())
                .withHeight(param.getHeight())
                .build());
        return imageModel.call(prompt);
    }
}
