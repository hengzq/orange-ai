package cn.hengzq.orange.ai.core.biz.chat.handler.impl;

import cn.hengzq.orange.ai.common.biz.agent.vo.AgentVO;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.core.biz.agent.service.AgentService;
import cn.hengzq.orange.ai.core.biz.chat.handler.AbstractChatHandler;
import cn.hengzq.orange.ai.core.biz.chat.handler.ChatContext;
import cn.hengzq.orange.ai.core.biz.chat.handler.ChatHandler;
import cn.hengzq.orange.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 加载智能体
 */
@Slf4j
@Component
@AllArgsConstructor
public class LoadAgentHandler extends AbstractChatHandler {

    private final AgentService agentService;

    @Override
    protected void before(ChatContext context) {
        log.info("加载智能体 handler before: {}", context);
    }

    @Override
    protected void execute(ChatContext context) {
        AgentVO agent = agentService.getById(context.getAgentId());
        if (Objects.isNull(agent)) {
            log.error("智能体不存在 modelId: {}", context.getAgentId());
            throw new ServiceException(AIModelErrorCode.MODEL_DATA_NOT_EXIST);
        }
        context.setAgent(agent);
    }

    @Override
    protected void after(ChatContext context) {
        log.info("加载智能体 handler after: {}", context);

    }
}
