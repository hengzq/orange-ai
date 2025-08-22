package cn.hengzq.orange.ai.common.biz.image.service;

import cn.hengzq.orange.ai.common.biz.image.dto.ImageModelGenerateParam;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.*;

import java.util.List;

@Slf4j
public abstract class AbstractImageModelService implements ImageModelService {

    /**
     * ChatModel缓存
     * key：密钥API_KEY
     * value：基于KEY创建的ChatModel
     */
    protected static final Cache<String, ImageModel> MODEL_LFU_CACHE = CacheUtil.newLFUCache(100);

    /**
     * 创建ChatModel
     */
    protected abstract ImageModel createImageModel(ModelVO model);


    @Override
    public List<String> listModel() {
        return List.of();
    }

    @Override
    public ImageModel getOrCreateImageModel(ModelVO model) {
        return MODEL_LFU_CACHE.get(model.getApiKey(), () -> createImageModel(model));
    }

    @Override
    public ImageResponse textToImage(ImageModelGenerateParam param) {

        ImageModel imageModel = this.getOrCreateImageModel(param.getModel());

        ImageOptions options = ImageOptionsBuilder.builder()
                .model(param.getModel().getModelName())
                .height(param.getHeight())
                .width(param.getWidth())
                .N(param.getQuantity())
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(param.getPrompt(), options);
        return imageModel.call(imagePrompt);
    }
}
