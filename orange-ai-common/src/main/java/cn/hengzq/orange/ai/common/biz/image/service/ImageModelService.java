package cn.hengzq.orange.ai.common.biz.image.service;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.biz.image.vo.param.GenerateImageParam;
import org.springframework.ai.image.ImageResponse;

public interface ImageModelService {

    PlatformEnum getPlatform();

    ImageResponse textToImage(GenerateImageParam param);
}
