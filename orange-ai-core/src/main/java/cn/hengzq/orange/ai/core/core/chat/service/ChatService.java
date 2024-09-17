package cn.hengzq.orange.ai.core.core.chat.service;

import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import reactor.core.publisher.Flux;

public interface ChatService {

    Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param);

}
