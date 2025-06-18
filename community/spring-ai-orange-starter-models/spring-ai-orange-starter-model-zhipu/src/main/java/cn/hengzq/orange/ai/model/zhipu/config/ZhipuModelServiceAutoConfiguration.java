package cn.hengzq.orange.ai.model.zhipu.config;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.model.zhipu.chat.ZhipuChatModelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@Slf4j
@AutoConfiguration
public class ZhipuModelServiceAutoConfiguration {

    @Bean
    public ChatModelService zhipuChatModelService() {
        return new ZhipuChatModelServiceImpl();
    }

}
