package cn.hengzq.orange.ai.core.biz.knowledge.service;


import cn.hengzq.orange.ai.common.biz.knowledge.constant.FileStatusEnum;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.*;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.*;
import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;
import java.util.Map;

/**
 * @author hengzq
 */
public interface KnowledgeDocService {

    Boolean deleteById(String id);

    KnowledgeDocVO getById(String id);

    List<KnowledgeDocVO> list(KnowledgeDocListParam param);

    PageDTO<KnowledgeDocVO> page(KnowledgeDocumentPageParam param);

    WebContentVO getMarkdownFromUrl(UrlParam param);

    String addTextToKnowledge(AddTextToKnowledgeParam param);

    List<KnowledgeDocSplitVO> split(KnowledgeDocSplitParam param);

    Boolean batchAddDocumentAndSlice(AddDocumentAndSliceParam param);

    void updateFileStatusById(String docId, FileStatusEnum fileStatusEnum);

    Map<String, KnowledgeDocVO> mapKnowledgeDocByIds(List<String> docIds);

}
