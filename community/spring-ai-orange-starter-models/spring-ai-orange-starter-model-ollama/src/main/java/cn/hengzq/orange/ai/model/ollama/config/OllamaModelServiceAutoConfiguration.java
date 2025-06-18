package cn.hengzq.orange.ai.model.ollama.config;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import cn.hengzq.orange.ai.model.ollama.chat.OllamaChatModelServiceImpl;
import cn.hengzq.orange.ai.model.ollama.embedding.OllamaEmbeddingModelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@Slf4j
@AutoConfiguration
public class OllamaModelServiceAutoConfiguration {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public ChatModelService ollamaChatModelService() {
        return new OllamaChatModelServiceImpl();
    }


    @Bean
    public EmbeddingModelService ollamaEmbeddingModelService() {
        return new OllamaEmbeddingModelServiceImpl();
    }
}
