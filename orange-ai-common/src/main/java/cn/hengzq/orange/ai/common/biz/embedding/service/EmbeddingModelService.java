package cn.hengzq.orange.ai.common.biz.embedding.service;


import cn.hengzq.orange.ai.common.biz.model.dto.ModelResponse;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import org.springframework.ai.embedding.EmbeddingModel;

import java.util.List;

public interface EmbeddingModelService {

    PlatformEnum getPlatform();


    EmbeddingModel getOrCreateEmbeddingModel(ModelResponse model);

    /**
     * 获取或创建向量模型
     */
    EmbeddingModel getOrCreateEmbeddingModel(String model, String baseUrl, String apiKey);

    /**
     * 模型列表
     */
    List<String> listModel();


    List<float[]> embed(List<String> texts);
}
