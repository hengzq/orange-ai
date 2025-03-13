package cn.hengzq.orange.ai.common.biz.vectorstore.service;

import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import cn.hengzq.orange.ai.common.biz.vectorstore.vo.param.VectorDataListParam;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

public interface VectorStoreService {

    /**
     * 获取数据库类型
     */
    VectorDatabaseEnum getVectorDatabaseType();

    /**
     * 获取向量数据库供应商
     */
    PlatformEnum getEmbeddingModelPlatform();

    VectorStore getVectorStore();



    void add(List<Document> documents);

    List<Document> list(VectorDataListParam param);
}
