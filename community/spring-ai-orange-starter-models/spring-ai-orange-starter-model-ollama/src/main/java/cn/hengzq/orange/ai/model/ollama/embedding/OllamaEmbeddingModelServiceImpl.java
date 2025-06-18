package cn.hengzq.orange.ai.model.ollama.embedding;

import cn.hengzq.orange.ai.common.biz.embedding.service.AbstractEmbeddingModelService;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.model.ollama.constant.EmbeddingModelEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class OllamaEmbeddingModelServiceImpl extends AbstractEmbeddingModelService {

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.OLLAMA;
    }

    @Override
    protected EmbeddingModel createEmbeddingModel(ModelVO model) {
        OllamaApi ollamaApi = OllamaApi.builder()
                .baseUrl(model.getBaseUrl())
                .build();
        return OllamaEmbeddingModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(OllamaOptions.builder()
                        .model(model.getModelName())
                        .build())
                .build();
    }

    @Override
    public List<String> listModel() {
        return EmbeddingModelEnum.getModelList();
    }

    @Override
    public List<float[]> embed(List<String> texts) {
        return null;
    }
}
