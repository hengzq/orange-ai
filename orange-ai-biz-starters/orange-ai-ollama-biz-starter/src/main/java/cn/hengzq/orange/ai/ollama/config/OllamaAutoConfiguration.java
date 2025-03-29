package cn.hengzq.orange.ai.ollama.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
public class OllamaAutoConfiguration {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

//    @Bean
//    public ChatModelService ollamaChatModelService(OllamaChatModel ollamaChatModel) {
//        return new OllamaChatModelServiceImpl(ollamaChatModel);
//    }

}
