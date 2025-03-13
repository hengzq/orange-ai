package cn.hengzq.orange.ai.vectorstore.milvus.service;

import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import cn.hengzq.orange.ai.common.biz.vectorstore.vo.param.VectorDataListParam;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.milvus.MilvusVectorStore;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class MilvusVectorStoreServiceImpl implements VectorStoreService {

    private final MilvusVectorStore milvusVectorStore;

    private final PlatformEnum embeddingModelPlatform;

    @Override
    public VectorDatabaseEnum getVectorDatabaseType() {
        return VectorDatabaseEnum.MILVUS;
    }

    @Override
    public PlatformEnum getEmbeddingModelPlatform() {
        return this.embeddingModelPlatform;
    }

    @Override
    public VectorStore getVectorStore() {
        return this.milvusVectorStore;
    }

    @Override
    public void add(List<Document> documents) {
        milvusVectorStore.add(documents);
    }

    @Override
    public List<Document> list(VectorDataListParam param) {
        return milvusVectorStore.similaritySearch(SearchRequest.builder().query(param.getText()).build());
    }

}
