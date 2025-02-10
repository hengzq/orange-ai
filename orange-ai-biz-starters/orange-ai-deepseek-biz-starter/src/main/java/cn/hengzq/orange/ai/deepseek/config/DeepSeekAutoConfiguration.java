package cn.hengzq.orange.ai.deepseek.config;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.deepseek.chat.DeepSeekChatModelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(DeepSeekStorageProperties.class)
public class DeepSeekAutoConfiguration {

    @Bean
    public ChatModelService deepSeekChatModelService(DeepSeekStorageProperties deepSeekStorageProperties) {
        return new DeepSeekChatModelServiceImpl(deepSeekStorageProperties);
    }
//
//    @Bean
//    public ImageModelService dashScopeImageModelService(DashScopeImageModel dashScopeImageModel) {
//        return new DashScopeImageModelServiceImpl(dashScopeImageModel);
//    }
}
