package cn.hengzq.orange.ai.vectorstore.milvus.config;

import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import cn.hengzq.orange.ai.vectorstore.milvus.service.MilvusVectorStoreServiceImpl;
import io.milvus.client.MilvusServiceClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Milvus Vector Store 配置类 参考Spring AI
 */
@AutoConfiguration
public class MilvusVectorStoreServiceAutoConfiguration {

    @Bean
    public VectorStoreService milvusService(MilvusServiceClient milvusServiceClient) {
        return new MilvusVectorStoreServiceImpl(milvusServiceClient);
    }

}
