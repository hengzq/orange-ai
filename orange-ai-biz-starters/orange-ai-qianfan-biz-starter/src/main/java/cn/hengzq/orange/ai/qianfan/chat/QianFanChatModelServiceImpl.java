package cn.hengzq.orange.ai.qianfan.chat;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.biz.chat.converter.MessageConverter;
import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.chat.vo.TokenUsageVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.ChatSessionRecordVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationReplyVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ConversationParam;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.qianfan.QianFanChatModel;
import org.springframework.ai.qianfan.QianFanChatOptions;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class QianFanChatModelServiceImpl implements ChatModelService {

    private final QianFanChatModel chatModel;

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.QIAN_FAN;
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param) {
        return conversationStream(param, List.of());
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param, List<ChatSessionRecordVO> contextMessageList) {
        List<Message> messages = new ArrayList<>(MessageConverter.toMessageList(contextMessageList));
        messages.add(new UserMessage(param.getPrompt()));

        Prompt prompt = new Prompt(messages, QianFanChatOptions.builder()
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
