package cn.hengzq.orange.ai.common.service.chat;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.vo.chat.ChatSessionRecordVO;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import cn.hengzq.orange.common.result.Result;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatModelService {

    PlatformEnum getPlatform();

    Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param);

    /**
     * 根据上下文对话,返回信息
     */
    Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param, List<ChatSessionRecordVO> contextMessageList);

}
