package cn.hengzq.orange.ai.vectorstore.milvus.service;

import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import cn.hengzq.orange.ai.common.biz.vectorstore.service.AbstractVectorStoreService;
import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.milvus.MilvusVectorStore;

@Slf4j
@AllArgsConstructor
public class MilvusVectorStoreServiceImpl extends AbstractVectorStoreService {

    private final static String DEFAULT_DATABASE_NAME = "default";

    private final MilvusServiceClient milvusServiceClient;

    @Override
    public VectorDatabaseEnum getVectorDatabaseType() {
        return VectorDatabaseEnum.MILVUS;
    }

    @Override
    public VectorStore createVectorStore(String collectionName, EmbeddingModel embeddingModel) {
        MilvusVectorStore vectorStore = MilvusVectorStore.builder(this.milvusServiceClient, embeddingModel)
                .collectionName(collectionName)
                .databaseName(DEFAULT_DATABASE_NAME)
                .indexType(IndexType.IVF_FLAT)
                .metricType(MetricType.COSINE)
                .batchingStrategy(new TokenCountBatchingStrategy())
                .initializeSchema(true)
                .build();
        try {
            vectorStore.afterPropertiesSet();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return vectorStore;
    }


}
