package cn.hengzq.orange.ai.alibaba.config;

import cn.hengzq.orange.ai.alibaba.chat.DashScopeChatModelServiceImpl;
import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class AlibabaAutoConfiguration {

    @Bean
    public ChatModelService tongYiChatModelService(DashScopeChatModel dashScopeChatModel) {
        return new DashScopeChatModelServiceImpl(dashScopeChatModel);
    }
}
