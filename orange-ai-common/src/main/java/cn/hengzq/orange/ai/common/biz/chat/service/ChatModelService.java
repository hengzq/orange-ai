package cn.hengzq.orange.ai.common.biz.chat.service;

import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationReplyVO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.common.result.Result;
import org.springframework.ai.chat.model.ChatModel;
import reactor.core.publisher.Flux;

public interface ChatModelService {

    PlatformEnum getPlatform();

    /**
     * 获取对话模型
     */
    ChatModel getChatModel();

    /**
     * 根据上下文对话,返回信息
     */
    Flux<Result<ConversationReplyVO>> conversationStream(ChatModelConversationParam param);


}
