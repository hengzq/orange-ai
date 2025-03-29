package cn.hengzq.orange.ai.common.biz.embedding.service;

import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
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

    protected abstract EmbeddingModel createEmbeddingModel(String model, String apiKey);

    @Override
    public EmbeddingModel getOrCreateEmbeddingModel(ModelVO model) {
        String key = model.getModelName() + "_" + model.getApiKey();
        if (embeddingModelCache.containsKey(key)) {
            return embeddingModelCache.get(key);
        }
        EmbeddingModel chatModel = createEmbeddingModel(model.getModelName(), model.getApiKey());
        embeddingModelCache.put(key, chatModel);
        return chatModel;
    }

    @Override
    public EmbeddingModel getOrCreateEmbeddingModel(String model, String apiKey) {
        String key = model + "_" + apiKey;
        if (embeddingModelCache.containsKey(key)) {
            return embeddingModelCache.get(key);
        }
        EmbeddingModel chatModel = createEmbeddingModel(model, apiKey);
        embeddingModelCache.put(key, chatModel);
        return chatModel;
    }

    @Override
    public List<String> listModel() {
        return List.of();
    }


}
