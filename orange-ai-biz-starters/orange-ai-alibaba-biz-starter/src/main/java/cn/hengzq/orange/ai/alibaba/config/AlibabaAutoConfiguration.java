package cn.hengzq.orange.ai.alibaba.config;

import cn.hengzq.orange.ai.alibaba.chat.DashScopeChatModelServiceImpl;
import cn.hengzq.orange.ai.alibaba.embedding.DashScopeEmbeddingModelServiceImpl;
import cn.hengzq.orange.ai.alibaba.image.DashScopeImageModelServiceImpl;
import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import cn.hengzq.orange.ai.common.biz.image.service.ImageModelService;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
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

    @Bean
    public EmbeddingModelService dashScopeEmbeddingModelService(DashScopeEmbeddingModel dashScopeEmbeddingModel) {
        return new DashScopeEmbeddingModelServiceImpl(dashScopeEmbeddingModel);
    }
}
