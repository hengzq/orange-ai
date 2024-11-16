package cn.hengzq.orange.ai.core.biz.chat.service;

import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationReplyVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ConversationParam;
import reactor.core.publisher.Flux;

public interface ChatService {

    ConversationReplyVO conversation(ConversationParam param);


    Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param);

}
