package cn.hengzq.orange.ai.core.biz.embedding.service;

import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmbeddingModelServiceFactory {

    private final Map<PlatformEnum, EmbeddingModelService> modelServiceMap;

    public EmbeddingModelServiceFactory(List<EmbeddingModelService> embeddingModelServices) {
        this.modelServiceMap = new HashMap<>(embeddingModelServices.size());
        embeddingModelServices.forEach(embeddingModelService ->
                modelServiceMap.put(embeddingModelService.getPlatform(), embeddingModelService)
        );
    }

    public EmbeddingModelService getEmbeddingModelService(PlatformEnum platform) {
        return modelServiceMap.get(platform);
    }
}
