package cn.hengzq.orange.ai.core.biz.chat.service;

import cn.hengzq.orange.ai.common.biz.chat.vo.param.AgentConversationStreamParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.CompletionsParam;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ConversationStreamParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

public interface ChatService {

    ConversationResponse conversation(ConversationStreamParam param);


    Flux<Result<ConversationResponse>> conversationStream(ConversationStreamParam param);

    Flux<Result<ConversationResponse>> agentConversationStream(AgentConversationStreamParam param);
}
