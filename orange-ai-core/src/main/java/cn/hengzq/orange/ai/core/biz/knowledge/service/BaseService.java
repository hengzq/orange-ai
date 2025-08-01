package cn.hengzq.orange.ai.core.biz.knowledge.service;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.BaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBaseListParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBasePageParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateKnowledgeBaseParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;
import java.util.Map;

/**
 * @author hengzq
 */
public interface BaseService {

    String add(AddKnowledgeBaseParam param);

    Boolean removeById(String id);

    Boolean updateById(String id, UpdateKnowledgeBaseParam request);

    Boolean updateEnabledById(String id, boolean enabled);

    BaseVO getById(String id);

    List<BaseVO> list(KnowledgeBaseListParam param);

    PageDTO<BaseVO> page(KnowledgeBasePageParam param);

    Map<String, BaseVO> mapKnowledgeBaseByIds(List<String> baseIds);

}
