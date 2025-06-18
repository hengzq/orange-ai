package cn.hengzq.orange.ai.model.deepseek.config;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.model.deepseek.chat.DeepseekChatModelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@Slf4j
@AutoConfiguration
public class DeepseekModelServiceAutoConfiguration {

    @Bean
    public ChatModelService deepseekChatModelService() {
        return new DeepseekChatModelServiceImpl();
    }


}
