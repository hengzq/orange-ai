package cn.hengzq.orange.ai.core.biz.knowledge.service;


import cn.hengzq.orange.ai.common.biz.knowledge.constant.DocStatusEnum;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.DocSplitVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.DocVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.WebContentVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.*;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;
import java.util.Map;

/**
 * @author hengzq
 */
public interface DocService {

    Boolean deleteById(String id);

    /**
     * 仅根据知识库ID，删除关联知识，不删除相关切片
     *
     * @param baseId 知识库ID
     * @return 是否成功删除
     */
    Boolean deleteByBaseId(String baseId);

    DocVO getById(String id);

    List<DocVO> list(KnowledgeDocListParam param);

    PageDTO<DocVO> page(KnowledgeDocumentPageParam param);

    WebContentVO getMarkdownFromUrl(UrlParam param);

    String addTextToKnowledge(AddTextToKnowledgeParam param);

    List<DocSplitVO> split(DocSplitParam param);

    Boolean batchAdd(AddDocAndChunkParam param);

    void updateFileStatusById(String docId, DocStatusEnum docStatusEnum);

    Map<String, DocVO> mapKnowledgeDocByIds(List<String> docIds);

}
