package cn.hengzq.orange.ai.alibaba.chat;

import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class DashScopeChatModelServiceImpl implements ChatModelService {

    private final DashScopeChatModel chatModel;

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.ALI_BAI_LIAN;
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param) {
        Prompt prompt = new Prompt(new UserMessage(param.getPrompt()), DashScopeChatOptions.builder()
                .withModel(param.getModelCode())
                .build());
        Flux<ChatResponse> stream = chatModel.stream(prompt);
        return stream
                .takeWhile(chatResponse -> Objects.nonNull(chatResponse) && Objects.nonNull(chatResponse.getResult())
                        && Objects.nonNull(chatResponse.getResult().getOutput()))
                .map(chatResponse -> {
                    String content = chatResponse.getResult().getOutput().getContent();
                    ConversationReplyVO replyVO = ConversationReplyVO.builder()
                            .content(content)
                            .build();
                    return ResultWrapper.ok(replyVO);
                });
    }

}
