package cn.hengzq.orange.ai.zhipu.config;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.zhipu.chat.ZhiPuChatModelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@Slf4j
@AutoConfiguration
public class ZhiPuAutoConfiguration {

    @Bean
    public ChatModelService zhiPuChatModelService(ZhiPuAiChatModel chatModel) {
        return new ZhiPuChatModelServiceImpl(chatModel);
    }
}
