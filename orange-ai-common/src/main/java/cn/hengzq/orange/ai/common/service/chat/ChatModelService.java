package cn.hengzq.orange.ai.common.service.chat;

import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import reactor.core.publisher.Flux;

public interface ChatModelService {

    PlatformEnum getPlatform();


    Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param);
}
