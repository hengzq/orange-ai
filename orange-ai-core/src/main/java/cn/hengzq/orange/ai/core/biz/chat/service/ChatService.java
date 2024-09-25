package cn.hengzq.orange.ai.core.biz.chat.service;

import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import reactor.core.publisher.Flux;

public interface ChatService {

    ConversationReplyVO conversation(ConversationParam param);


    Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param);

}
