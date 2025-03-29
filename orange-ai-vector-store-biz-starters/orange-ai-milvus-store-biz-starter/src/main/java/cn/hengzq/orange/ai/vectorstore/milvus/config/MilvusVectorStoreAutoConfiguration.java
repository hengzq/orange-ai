package cn.hengzq.orange.ai.vectorstore.milvus.config;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.BatchingStrategy;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Milvus Vector Store 配置类 参考Spring AI
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(MilvusServiceClientProperties.class)
public class MilvusVectorStoreAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(BatchingStrategy.class)
    BatchingStrategy milvusBatchingStrategy() {
        return new TokenCountBatchingStrategy();
    }


    @Bean
    public MilvusServiceClient milvusServiceClient(MilvusServiceClientProperties properties) {
        return new MilvusServiceClient(ConnectParam.newBuilder()
                .withHost(properties.getHost())
                .withPort(properties.getPort())
                .build());
    }


}
