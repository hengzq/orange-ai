package cn.hengzq.orange.ai.alibaba.chat;

import cn.hengzq.orange.ai.alibaba.constant.ChatModelEnum;
import cn.hengzq.orange.ai.common.biz.chat.constant.ConverstationEventEnum;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.service.AbstractChatModelService;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationReplyVO;
import cn.hengzq.orange.ai.common.biz.model.constant.ModelConstant;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class DashScopeChatModelServiceImpl extends AbstractChatModelService {


    /**
     * 获取当前ChatMode所属平台类型。
     *
     * @return 返回枚举类型的平台，此函数固定返回ALI_BAI_LIAN。
     */
    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.ALI_BAI_LIAN;
    }


    /**
     * 创建聊天模型。
     *
     * @return 返回一个新的DashScopeChatModel实例，该实例使用指定的API密钥进行初始化。
     */
    @Override
    protected ChatModel createChatModel(ModelVO model) {
        String apiKey = SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).decryptStr(model.getApiKey());
        DashScopeApi dashScopeApi = new DashScopeApi(apiKey);
        return new DashScopeChatModel(dashScopeApi);
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ChatModelConversationParam param) {
        List<Message> messages = CollUtil.isEmpty(param.getMessages()) ? new ArrayList<>() : new ArrayList<>(param.getMessages());
        messages.add(new UserMessage(param.getPrompt()));

        Prompt prompt = new Prompt(messages, DashScopeChatOptions.builder()
                .withModel(param.getModel().getModelName())
                .build());
        Flux<ChatResponse> stream = this.getOrCreateChatModel(param.getModel()).stream(prompt);

        return stream
                .takeWhile(chatResponse -> Objects.nonNull(chatResponse) && Objects.nonNull(chatResponse.getResult())
                        && Objects.nonNull(chatResponse.getResult().getOutput()))
                .map(chatResponse -> {
                    if (log.isDebugEnabled()) {
                        log.debug("chatResponse: {}", chatResponse);
                    }
                    Usage usage = chatResponse.getMetadata().getUsage();
                    String content = chatResponse.getResult().getOutput().getText();
                    String finishReason = chatResponse.getResult().getMetadata().getFinishReason();

                    ConversationReplyVO replyVO = ConversationReplyVO.builder()
                            .event("STOP".equalsIgnoreCase(finishReason) ? ConverstationEventEnum.FINISHED : ConverstationEventEnum.REPLY)
                            .content(content)
//                            .tokenUsage(TokenUsageVO.builder()
//                                    .promptTokens(usage.getPromptTokens())
//                                    .generationTokens(usage.getGenerationTokens())
//                                    .totalTokens(usage.getTotalTokens())
//                                    .build())
                            .build();
                    return ResultWrapper.ok(replyVO);
                });
    }

    @Override
    public List<String> listModel() {
        return ChatModelEnum.getModelList();
    }
}
