package cn.hengzq.orange.ai.core.biz.knowledge.service;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeBaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBaseListParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBasePageParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import cn.hengzq.orange.common.dto.PageDTO;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;
import java.util.Map;

/**
 * @author hengzq
 */
public interface KnowledgeBaseService {

    String add(AddKnowledgeBaseParam param);

    Boolean removeById(String id);

    Boolean updateById(String id, UpdateKnowledgeBaseParam request);

    KnowledgeBaseVO getById(String id);

    List<KnowledgeBaseVO> list(KnowledgeBaseListParam param);

    PageDTO<KnowledgeBaseVO> page(KnowledgeBasePageParam param);

    Map<String, KnowledgeBaseVO> mapKnowledgeBaseByIds(List<String> baseIds);

    VectorStore getVectorStoreById(String id);
}
