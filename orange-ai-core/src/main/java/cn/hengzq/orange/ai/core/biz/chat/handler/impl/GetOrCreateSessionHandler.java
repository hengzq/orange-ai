package cn.hengzq.orange.ai.core.biz.chat.handler.impl;

import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import cn.hengzq.orange.ai.common.biz.session.vo.SessionVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionParam;
import cn.hengzq.orange.ai.core.biz.chat.handler.AbstractChatHandler;
import cn.hengzq.orange.ai.core.biz.chat.handler.ChatContext;
import cn.hengzq.orange.ai.core.biz.session.service.SessionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 加载模型处理器
 */
@Slf4j
@Component
@AllArgsConstructor
public class GetOrCreateSessionHandler extends AbstractChatHandler {

    private final SessionService sessionService;

    @Override
    protected void before(ChatContext context) {

    }

    @Override
    protected void execute(ChatContext context) {
        String sessionId = context.getSessionId();
        String associationId = SessionTypeEnum.AGENT.equals(context.getSessionType()) ? context.getAgentId() : null;
        if (Objects.isNull(sessionId)) {
            sessionId = sessionService.add(AddSessionParam.builder()
                    .modelId(context.getModelId())
                    .sessionType(context.getSessionType())
                    .associationId(associationId)
                    .name(context.getPrompt())
                    .build());
            context.setSessionId(sessionId);
            return;
        }
        SessionVO session = sessionService.getById(sessionId);
        if (Objects.nonNull(session)) {
            context.setSessionId(session.getId());
            return;
        }
        sessionId = sessionService.add(AddSessionParam.builder()
                .modelId(context.getModelId())
                .sessionType(context.getSessionType())
                .associationId(associationId)
                .name(context.getPrompt())
                .build());
        context.setSessionId(sessionId);
    }

    @Override
    protected void after(ChatContext context) {

    }

}
