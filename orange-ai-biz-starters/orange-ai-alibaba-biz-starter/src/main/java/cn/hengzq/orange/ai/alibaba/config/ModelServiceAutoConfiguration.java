package cn.hengzq.orange.ai.alibaba.config;

import cn.hengzq.orange.ai.alibaba.chat.DashScopeChatModelServiceImpl;
import cn.hengzq.orange.ai.alibaba.embedding.DashScopeEmbeddingModelServiceImpl;
import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ModelServiceAutoConfiguration {

    @Bean
    public ChatModelService dashScopeChatModelService() {
        return new DashScopeChatModelServiceImpl();
    }

    //
//    @Bean
//    public ImageModelService dashScopeImageModelService(DashScopeImageModel dashScopeImageModel) {
//        return new DashScopeImageModelServiceImpl(dashScopeImageModel);
//    }
//
    @Bean
    public EmbeddingModelService dashScopeEmbeddingModelService() {
        return new DashScopeEmbeddingModelServiceImpl();
    }
}
