package cn.hengzq.orange.ai.model.minimax.config;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.model.minimax.chat.MiniMaxChatModelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@Slf4j
@AutoConfiguration
public class MiniMaxModelServiceAutoConfiguration {

//    @Bean
//    public RestClient.Builder restClientBuilder() {
//        return RestClient.builder();
//    }

    @Bean
    public ChatModelService miniMaxChatModelService() {
        return new MiniMaxChatModelServiceImpl();
    }


}
