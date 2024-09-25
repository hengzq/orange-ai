package cn.hengzq.orange.ai.zhipu.chat;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import reactor.core.publisher.Flux;

@Slf4j
@AllArgsConstructor
public class ZhiPuChatModelServiceImpl implements ChatModelService {

    private final ZhiPuAiChatModel chatModel;

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.ZHI_PU;
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param) {
        Prompt prompt = new Prompt(new UserMessage(param.getPrompt()), ZhiPuAiChatOptions.builder()
                .withModel(param.getModelCode())
                .build());
        Flux<ChatResponse> stream = chatModel.stream(prompt);
        return stream.map(chatResponse -> {
            String content = chatResponse.getResult().getOutput().getContent();
            ConversationReplyVO replyVO = ConversationReplyVO.builder()
                    .content(content)
                    .build();
            return ResultWrapper.ok(replyVO);
        });
    }

}
