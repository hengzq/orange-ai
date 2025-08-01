package cn.hengzq.orange.ai.core.biz.knowledge.service;


import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

/**
 * 切片向量化相关操作
 *
 * @author hengzq
 */
public interface VectorSyncService {

    /**
     * 新增刷新
     */
    void refreshDocByDocIds(List<String> docIds);

    /**
     * 强制刷新，会删除已经向量化的数据
     */
    void forcedRefreshDocByDocIds(List<String> docIds);


    /**
     * 根据知识库ID 获取 VectorStore
     *
     * @param baseId 知识库ID
     * @return 返回 VectorStore
     */
    VectorStore getVectorStoreByBaseId(String baseId);

}
