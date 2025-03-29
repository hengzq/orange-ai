package cn.hengzq.orange.ai.core.biz.chat.handler;

public interface ChatHandler {

    ChatHandler next(ChatHandler next);

    void handle(ChatContext context);
}
