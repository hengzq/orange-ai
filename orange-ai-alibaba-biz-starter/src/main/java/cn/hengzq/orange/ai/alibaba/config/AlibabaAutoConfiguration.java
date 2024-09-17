package cn.hengzq.orange.ai.alibaba.config;

import cn.hengzq.orange.ai.alibaba.chat.TongYiChatModelServiceImpl;
import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import com.alibaba.cloud.ai.tongyi.chat.TongYiChatModel;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class AlibabaAutoConfiguration {

    @Bean
    public ChatModelService tongYiChatModelService(TongYiChatModel tongYiChatModel) {
        return new TongYiChatModelServiceImpl(tongYiChatModel);
    }
}
