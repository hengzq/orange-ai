package cn.hengzq.orange.ai.common.biz.vectorstore.service;

import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

public interface VectorStoreService {

    /**
     * 获取数据库类型
     */
    VectorDatabaseEnum getVectorDatabaseType();

    VectorStore createVectorStore(String collectionName, EmbeddingModel embeddingModel);

    VectorStore getOrCreateVectorStore(String collectionName, EmbeddingModel embeddingModel);

    void add(String collectionName, EmbeddingModel embeddingModel, List<Document> documents);

    void deleteByIds(String vectorCollectionName, EmbeddingModel embeddingModel, List<String> sliceIds);
}
