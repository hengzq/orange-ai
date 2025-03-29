package cn.hengzq.orange.ai.core.biz.chat.handler.impl;

import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionMessageParam;
import cn.hengzq.orange.ai.core.biz.chat.handler.AbstractChatHandler;
import cn.hengzq.orange.ai.core.biz.chat.handler.ChatContext;
import cn.hengzq.orange.ai.core.biz.session.service.SessionMessageService;
import cn.hengzq.orange.common.constant.GlobalConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 保存用户消息处理器
 */
@Slf4j
@Component
@AllArgsConstructor
public class SaveUserSessionMessageHandler extends AbstractChatHandler {

    private final SessionMessageService sessionMessageService;

    @Override
    protected void before(ChatContext context) {

    }

    @Override
    protected void execute(ChatContext context) {
        String questionId = sessionMessageService.add(AddSessionMessageParam.builder()
                .parentId(GlobalConstant.DEFAULT_PARENT_ID)
                .sessionId(context.getSessionId())
                .role(MessageTypeEnum.USER)
                .content(context.getPrompt())
                .build());

        context.setQuestionId(questionId);
    }

    @Override
    protected void after(ChatContext context) {

    }

}
