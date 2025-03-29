package cn.hengzq.orange.ai.core.biz.agent.service;


import cn.hengzq.orange.ai.common.biz.agent.vo.AgentVO;
import cn.hengzq.orange.ai.common.biz.agent.vo.ConversationStreamVO;
import cn.hengzq.orange.ai.common.biz.agent.vo.param.*;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.result.Result;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author hengzq
 */
public interface AgentService {

    String add(AddAgentParam request);

    Boolean removeById(String id);

    Boolean updateById(String id, UpdateAgentParam request);

    AgentVO getById(String id);

    List<AgentVO> list(AgentListParam query);

    PageDTO<AgentVO> page(AgentPageParam param);

    Flux<Result<ConversationStreamVO>> debugConversationStream(AgentDebugConversationParam param);
}
