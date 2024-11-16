package cn.hengzq.orange.ai.alibaba.image;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.biz.image.service.ImageModelService;
import cn.hengzq.orange.ai.common.biz.image.vo.param.GenerateImageParam;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;

@Slf4j
@AllArgsConstructor
public class DashScopeImageModelServiceImpl implements ImageModelService {

    private final DashScopeImageModel imageModel;

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.ALI_BAI_LIAN;
    }

    @Override
    public ImageResponse textToImage(GenerateImageParam param) {
        ImageOptions options = DashScopeImageOptions.builder()
                .withModel(param.getModelCode())
                .withHeight(param.getHeight())
                .withWidth(param.getWidth())
                .withN(param.getQuantity())
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(param.getPrompt(), options);
        return imageModel.call(imagePrompt);
    }


}
