package cn.hengzq.orange.ai.ollama.chat;

import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.service.AbstractChatModelService;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationReplyVO;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.common.result.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@AllArgsConstructor
public class OllamaChatModelServiceImpl extends AbstractChatModelService {

    private final OllamaChatModel ollamaChatModel;

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.OLLAMA;
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ChatModelConversationParam param) {
        return null;
    }

    @Override
    protected ChatModel createChatModel(ModelVO model) {
        return null;
    }
}
