package cn.hengzq.orange.ai.ollama.chat;

import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@AllArgsConstructor
public class OllamaChatModelServiceImpl implements ChatModelService {

    private final OllamaChatModel ollamaChatModel;

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.OLLAMA;
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param) {
        return null;
    }

}
