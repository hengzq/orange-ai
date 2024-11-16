package cn.hengzq.orange.ai.common.biz.chat.service;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.biz.chat.vo.ChatSessionRecordVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationReplyVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ConversationParam;
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
