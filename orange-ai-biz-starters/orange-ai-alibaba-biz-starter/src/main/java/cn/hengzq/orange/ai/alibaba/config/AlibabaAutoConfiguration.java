package cn.hengzq.orange.ai.alibaba.config;

import cn.hengzq.orange.ai.alibaba.chat.DashScopeChatModelServiceImpl;
import cn.hengzq.orange.ai.alibaba.image.DashScopeImageModelServiceImpl;
import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import cn.hengzq.orange.ai.common.service.image.ImageModelService;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class AlibabaAutoConfiguration {

    @Bean
    public ChatModelService dashScopeChatModelService(DashScopeChatModel dashScopeChatModel) {
        return new DashScopeChatModelServiceImpl(dashScopeChatModel);
    }

    @Bean
    public ImageModelService dashScopeImageModelService(DashScopeImageModel dashScopeImageModel) {
        return new DashScopeImageModelServiceImpl(dashScopeImageModel);
    }
}
