package cn.hengzq.orange.ai.model.alibaba.config;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import cn.hengzq.orange.ai.common.biz.image.service.ImageModelService;
import cn.hengzq.orange.ai.model.alibaba.chat.DashScopeChatModelServiceImpl;
import cn.hengzq.orange.ai.model.alibaba.embedding.DashScopeEmbeddingModelServiceImpl;
import cn.hengzq.orange.ai.model.alibaba.image.DashScopeImageModelServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class DashScopeModelServiceAutoConfiguration {

    @Bean
    public ChatModelService dashScopeChatModelService() {
        return new DashScopeChatModelServiceImpl();
    }


    @Bean
    public ImageModelService dashScopeImageModelService( ) {
        return new DashScopeImageModelServiceImpl();
    }

    @Bean
    public EmbeddingModelService dashScopeEmbeddingModelService() {
        return new DashScopeEmbeddingModelServiceImpl();
    }
}
