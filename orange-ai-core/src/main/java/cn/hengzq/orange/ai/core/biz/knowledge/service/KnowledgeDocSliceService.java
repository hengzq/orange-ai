package cn.hengzq.orange.ai.core.biz.knowledge.service;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeDocSliceVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.*;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;

/**
 * @author hengzq
 */
public interface KnowledgeDocSliceService {

    String add(AddDocSliceParam param);

    Boolean deleteById(String id);

    Boolean deleteByDocId(String docId);

    Boolean updateById(String id, UpdateDocSliceParam request);

    KnowledgeDocSliceVO getById(String id);

    List<KnowledgeDocSliceVO> list(KnowledgeDocSliceListParam param);

    PageDTO<KnowledgeDocSliceVO> page(KnowledgeDocSlicePageParam param);
}
