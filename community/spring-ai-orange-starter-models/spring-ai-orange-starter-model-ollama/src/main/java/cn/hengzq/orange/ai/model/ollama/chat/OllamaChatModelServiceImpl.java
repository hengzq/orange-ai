package cn.hengzq.orange.ai.model.ollama.chat;

import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelOptions;
import cn.hengzq.orange.ai.common.biz.chat.service.AbstractChatModelService;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.model.ollama.constant.ChatModelEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class OllamaChatModelServiceImpl extends AbstractChatModelService {

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.OLLAMA;
    }

    @Override
    protected ChatModel createChatModel(ModelVO model) {
        OllamaApi ollamaApi = OllamaApi.builder().baseUrl(model.getBaseUrl()).build();
        return OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(OllamaOptions.builder()
                        .model(model.getModelName())
                        .build())
                .build();
    }

    @Override
    protected ChatModel createChatModel(String model, String baseUrl, String apiKey) {
        OllamaApi ollamaApi = OllamaApi.builder().baseUrl(baseUrl).build();
        return OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(OllamaOptions.builder()
                        .model(model)
                        .build())
                .build();
    }

    @Override
    protected ChatOptions createChatOptions(ChatModelOptions options) {
        return OllamaOptions.builder()
                .model(options.getModel())
                .temperature(options.getTemperature())
                .build();
    }

    @Override
    public List<String> listModel() {
        return ChatModelEnum.getModelList();
    }
}
