package cn.hengzq.orange.ai.core.image.service;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.vo.image.TextToImageVO;
import cn.hengzq.orange.ai.common.vo.image.param.GenerateImageParam;
import org.springframework.ai.image.ImageResponse;

public interface ImageModelService {

    PlatformEnum getPlatform();

    ImageResponse textToImage(GenerateImageParam param);
}
