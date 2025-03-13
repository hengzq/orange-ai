package cn.hengzq.orange.ai.core.biz.embedding.service.impl;

import cn.hengzq.orange.ai.core.biz.embedding.service.EmbeddingModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.embedding.service.EmbeddingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService {

    private final EmbeddingModelServiceFactory embeddingModelServiceFactory;

    @Override
    public EmbeddingResponse embedForResponse(List<String> message) {
//        return embeddingModelServiceFactory.getEmbeddingModelService(PlatformEnum.ALI_BAI_LIAN)
//                .embedForResponse(message);
        return null;
    }

//    @Override
//    public List<float[]> embedToFloat(EmbedParam param) {
//        EmbeddingModelService embeddingModelService = embeddingModelServiceFactory.getEmbeddingModelService(param.getPlatform());
//        return embeddingModelService.embed(param.getTexts());
//    }
}
