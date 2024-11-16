package cn.hengzq.orange.ai.ollama.config;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.ollama.chat.OllamaChatModelServiceImpl;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
public class OllamaAutoConfiguration {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public ChatModelService ollamaChatModelService(OllamaChatModel ollamaChatModel) {
        return new OllamaChatModelServiceImpl(ollamaChatModel);
    }

}
