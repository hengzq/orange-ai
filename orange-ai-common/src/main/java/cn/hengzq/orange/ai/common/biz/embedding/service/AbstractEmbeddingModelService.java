package cn.hengzq.orange.ai.common.biz.embedding.service;

import cn.hengzq.orange.ai.common.biz.model.dto.ModelResponse;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import org.springframework.ai.embedding.EmbeddingModel;

import java.util.List;

public abstract class AbstractEmbeddingModelService implements EmbeddingModelService {

    /**
     * EmbeddingModel
     * key：密钥API_KEY
     * value：基于KEY创建的EmbeddingModel
     */
    protected static final Cache<String, EmbeddingModel> embeddingModelCache = CacheUtil.newLFUCache(100);

    protected abstract EmbeddingModel createEmbeddingModel(ModelResponse model);

    /**
     * 创建 EmbeddingModel
     *
     * @param model   模型
     * @param baseUrl 模型基础URL
     * @param apiKey  模型密钥
     * @return 返回 EmbeddingModel
     */
    protected abstract EmbeddingModel createEmbeddingModel(String model, String baseUrl, String apiKey);

    @Override
    public EmbeddingModel getOrCreateEmbeddingModel(ModelResponse model) {
        String key = model.getModelName() + "_" + model.getApiKey();
        if (embeddingModelCache.containsKey(key)) {
            return embeddingModelCache.get(key);
        }
        EmbeddingModel chatModel = createEmbeddingModel(model);
        embeddingModelCache.put(key, chatModel);
        return chatModel;
    }

    @Override
    public EmbeddingModel getOrCreateEmbeddingModel(String model, String baseUrl, String apiKey) {
        String key = model + "_" + apiKey;
        if (embeddingModelCache.containsKey(key)) {
            return embeddingModelCache.get(key);
        }
        EmbeddingModel embeddingModel = createEmbeddingModel(model, baseUrl, apiKey);
        embeddingModelCache.put(key, embeddingModel);
        return embeddingModel;
    }

    @Override
    public List<String> listModel() {
        return List.of();
    }


}
