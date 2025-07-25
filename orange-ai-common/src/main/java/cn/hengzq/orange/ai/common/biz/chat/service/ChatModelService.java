package cn.hengzq.orange.ai.common.biz.chat.service;

import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.common.result.Result;
import org.springframework.ai.chat.model.ChatModel;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatModelService {

    /**
     * 获取当前ChatMode所属平台类型。
     */
    PlatformEnum getPlatform();

    /**
     * 模型列表
     */
    List<String> listModel();

    /**
     * 获取对话模型
     */
    ChatModel getOrCreateChatModel(String model, String baseUrl, String apiKey);

    /**
     * 根据上下文对话,返回信息
     */
    Flux<Result<ConversationResponse>> conversationStream(ChatModelConversationParam param);


    /**
     * 根据上下文对话,返回信息
     */
    ConversationResponse conversation(ChatModelConversationParam param);

}
