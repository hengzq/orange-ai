package cn.hengzq.orange.ai.core.biz.embedding.service;

import org.springframework.ai.embedding.EmbeddingResponse;

import java.util.List;

public interface EmbeddingService {
    EmbeddingResponse embedForResponse(List<String> message);

//    List<float[]> embedToFloat(EmbedParam param);
}
