package cn.hengzq.orange.ai.common.biz.image.service;

import cn.hengzq.orange.ai.common.biz.image.dto.ImageModelGenerateParam;
import cn.hengzq.orange.ai.common.biz.model.dto.ModelResponse;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageResponse;

import java.util.List;

public interface ImageModelService {

    PlatformEnum getPlatform();

    /**
     * 模型列表
     */
    List<String> listModel();

    /**
     * 获取对话模型
     */
    ImageModel getOrCreateImageModel(ModelResponse model);


    ImageResponse textToImage(ImageModelGenerateParam param);


}
