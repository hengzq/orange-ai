package cn.hengzq.orange.ai.zhipu.chat;

import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.service.AbstractChatModelService;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationReplyVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.TokenUsageVO;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
public class ZhiPuChatModelServiceImpl extends AbstractChatModelService {

    private final ZhiPuAiChatModel chatModel;

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.ZHI_PU;
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ChatModelConversationParam param) {
        Stream<Message> messageStream = Stream.concat(
                param.getMessages().stream(),
                Stream.of(new UserMessage(param.getPrompt()))
        );

        Prompt prompt = new Prompt(messageStream.collect(Collectors.toList()), ZhiPuAiChatOptions.builder()
                .model(param.getModel().getModelName())
                .build());
        Flux<ChatResponse> stream = chatModel.stream(prompt);
        return stream.map(chatResponse -> {
            if (log.isDebugEnabled()) {
                log.debug("chatResponse: {}", chatResponse);
            }
            Usage usage = chatResponse.getMetadata().getUsage();
            String content = chatResponse.getResult().getOutput().getText();
            ConversationReplyVO replyVO = ConversationReplyVO.builder()
                    .content(content)
                    .tokenUsage(TokenUsageVO.builder()
                            .promptTokens(Long.valueOf(usage.getPromptTokens()))
                            .generationTokens(Long.valueOf(usage.getCompletionTokens()))
                            .totalTokens(Long.valueOf(usage.getTotalTokens()))
                            .build())
                    .build();
            return ResultWrapper.ok(replyVO);
        });
    }

    @Override
    protected ChatModel createChatModel(ModelVO model) {
        return null;
    }
}
