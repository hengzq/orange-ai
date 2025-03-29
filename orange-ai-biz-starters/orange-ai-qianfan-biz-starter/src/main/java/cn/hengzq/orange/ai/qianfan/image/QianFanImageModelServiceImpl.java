package cn.hengzq.orange.ai.qianfan.image;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.biz.image.service.ImageModelService;
import cn.hengzq.orange.ai.common.biz.image.vo.param.GenerateImageParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.qianfan.QianFanImageModel;
import org.springframework.ai.qianfan.QianFanImageOptions;

@Slf4j
@AllArgsConstructor
public class QianFanImageModelServiceImpl implements ImageModelService {

    private final QianFanImageModel imageModel;

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.QIAN_FAN;
    }

    /**
     * {@link org.springframework.ai.qianfan.api.QianFanImageApi.ImageModel}
     */
    @Override
    public ImageResponse textToImage(GenerateImageParam param) {
        ImageOptions options = QianFanImageOptions.builder()
                .model(param.getModelCode())
                .height(param.getHeight())
                .width(param.getWidth())
                .N(param.getQuantity())
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(param.getPrompt(), options);
        return imageModel.call(imagePrompt);
    }

}
