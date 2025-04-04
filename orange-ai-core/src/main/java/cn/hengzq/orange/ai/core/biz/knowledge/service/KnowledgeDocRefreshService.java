package cn.hengzq.orange.ai.core.biz.knowledge.service;


import java.util.List;

/**
 * 切片向量化相关操作
 *
 * @author hengzq
 */
public interface KnowledgeDocRefreshService {

    /**
     * 新增刷新
     */
    void refreshDocByDocIds(List<String> docIds);

    /**
     * 强制刷新，会删除已经向量化的数据
     */
    void forcedRefreshDocByDocIds(List<String> docIds);

}
