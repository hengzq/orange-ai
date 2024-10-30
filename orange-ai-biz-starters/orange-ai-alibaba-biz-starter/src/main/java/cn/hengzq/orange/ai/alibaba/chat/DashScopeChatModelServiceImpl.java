package cn.hengzq.orange.ai.alibaba.chat;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.converter.MessageConverter;
import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import cn.hengzq.orange.ai.common.vo.TokenUsageVO;
import cn.hengzq.orange.ai.common.vo.chat.ChatSessionRecordVO;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
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
        return conversationStream(param, List.of());
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param, List<ChatSessionRecordVO> contextMessageList) {
        List<Message> messages = new ArrayList<>(MessageConverter.toMessageList(contextMessageList));
        messages.add(new UserMessage(param.getPrompt()));

        Prompt prompt = new Prompt(messages, DashScopeChatOptions.builder()
                .withModel(param.getModelCode())
                .build());
        Flux<ChatResponse> stream = chatModel.stream(prompt);
        return stream
                .takeWhile(chatResponse -> Objects.nonNull(chatResponse) && Objects.nonNull(chatResponse.getResult())
                        && Objects.nonNull(chatResponse.getResult().getOutput()))
                .map(chatResponse -> {
                    if (log.isDebugEnabled()) {
                        log.debug("chatResponse: {}", chatResponse);
                    }
                    Usage usage = chatResponse.getMetadata().getUsage();
                    String content = chatResponse.getResult().getOutput().getContent();
                    ConversationReplyVO replyVO = ConversationReplyVO.builder()
                            .content(content)
                            .tokenUsage(TokenUsageVO.builder()
                                    .promptTokens(usage.getPromptTokens())
                                    .generationTokens(usage.getGenerationTokens())
                                    .totalTokens(usage.getTotalTokens())
                                    .build())
                            .build();
                    return ResultWrapper.ok(replyVO);
                });
    }

}
