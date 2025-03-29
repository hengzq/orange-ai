package cn.hengzq.orange.ai.core.biz.chat.handler.impl;

import cn.hengzq.orange.ai.common.biz.chat.constant.AIChatErrorCode;
import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import cn.hengzq.orange.ai.core.biz.chat.handler.AbstractChatHandler;
import cn.hengzq.orange.ai.core.biz.chat.handler.ChatContext;
import cn.hengzq.orange.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 参数校验处理器
 */
@Slf4j
@Component
@AllArgsConstructor
public class ParamCheckHandler extends AbstractChatHandler {


    @Override
    protected void before(ChatContext context) {
        if (log.isDebugEnabled()) {
            log.debug("param check before, context:{}", context);
        }
    }

    @Override
    protected void execute(ChatContext context) {

    }

    @Override
    protected void after(ChatContext context) {
        log.info("after chat context:{}", context);
    }


}
