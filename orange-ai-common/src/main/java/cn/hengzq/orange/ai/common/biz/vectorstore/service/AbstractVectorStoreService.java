package cn.hengzq.orange.ai.common.biz.vectorstore.service;

import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import cn.hengzq.orange.common.util.Assert;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

@Slf4j
public abstract class AbstractVectorStoreService implements VectorStoreService {

    private static final Cache<String, VectorStore> VECTOR_STORE_LFU_CACHE = CacheUtil.newLFUCache(100);

    public VectorStore getOrCreateVectorStore(String collectionName, EmbeddingModel embeddingModel) {
        if (VECTOR_STORE_LFU_CACHE.containsKey(collectionName)) {
            return VECTOR_STORE_LFU_CACHE.get(collectionName);
        }
        VectorStore vectorStore = this.createVectorStore(collectionName, embeddingModel);
        VECTOR_STORE_LFU_CACHE.put(collectionName, vectorStore);
        return vectorStore;
    }

    @Override
    public void add(String collectionName, EmbeddingModel embeddingModel, List<Document> documents) {
        VectorStore vectorStore = this.getOrCreateVectorStore(collectionName, embeddingModel);
        Assert.nonNull(vectorStore, GlobalErrorCodeConstant.GLOBAL_BAD_REQUEST);
        vectorStore.add(documents);

    }

    @Override
    public void deleteByIds(String collectionName, EmbeddingModel embeddingModel, List<String> sliceIds) {
        if (CollUtil.isEmpty(sliceIds)) {
            return;
        }
        VectorStore vectorStore = this.getOrCreateVectorStore(collectionName, embeddingModel);
        Assert.nonNull(vectorStore, GlobalErrorCodeConstant.GLOBAL_BAD_REQUEST);
        vectorStore.delete(sliceIds);
    }

}
