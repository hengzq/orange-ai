package cn.hengzq.orange.ai.alibaba.chat;

import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.ai.tongyi.chat.TongYiChatModel;
import com.alibaba.cloud.ai.tongyi.chat.TongYiChatOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

@Slf4j
@AllArgsConstructor
public class TongYiChatModelServiceImpl implements ChatModelService {

    private final TongYiChatModel chatModel;

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.TONGYI;
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param) {
        Prompt prompt = new Prompt(new UserMessage(param.getPrompt()), TongYiChatOptions.builder()
                .withModel(param.getModelCode())
                .build());
        Flux<ChatResponse> stream = chatModel.stream(prompt);
        return stream.map(chatResponse -> {
            log.info("chat response: {}", JSONUtil.toJsonStr(chatResponse));
            String content = chatResponse.getResult().getOutput().getContent();
            ConversationReplyVO replyVO = ConversationReplyVO.builder()
                    .content(content)
                    .build();
            return ResultWrapper.ok(replyVO);
        });
    }

}
