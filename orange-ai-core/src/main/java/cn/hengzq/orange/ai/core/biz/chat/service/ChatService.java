package cn.hengzq.orange.ai.core.biz.chat.service;

import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ConversationStreamParam;
import cn.hengzq.orange.common.result.Result;
import reactor.core.publisher.Flux;

public interface ChatService {

    ConversationResponse conversation(ConversationStreamParam param);

    Flux<Result<ConversationResponse>> conversationStream(ConversationStreamParam param);

    Flux<Result<ConversationResponse>> stream(ChatModelConversationParam param);
}
