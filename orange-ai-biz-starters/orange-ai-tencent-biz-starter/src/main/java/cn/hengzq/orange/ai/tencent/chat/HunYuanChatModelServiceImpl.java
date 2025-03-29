package cn.hengzq.orange.ai.tencent.chat;

import cn.hengzq.orange.ai.common.biz.chat.constant.AIChatErrorCode;
import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.service.AbstractChatModelService;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationReplyVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.TokenUsageVO;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.common.exception.ServiceException;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.tencentcloudapi.common.SSEResponseModel;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.hunyuan.v20230901.HunyuanClient;
import com.tencentcloudapi.hunyuan.v20230901.models.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class HunYuanChatModelServiceImpl extends AbstractChatModelService {

    private final HunyuanClient hunyuanClient;

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.TENCENT;
    }


    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ChatModelConversationParam param) {
        // 实例化一个请求对象,每个接口都会对应一个request对象
        ChatCompletionsRequest req = new ChatCompletionsRequest();
        req.setModel(param.getModel().getModelName());
        req.setStream(Boolean.TRUE);

        List<Message> messages = new ArrayList<>();
        // 分装历史回话信息
        if (CollUtil.isNotEmpty(param.getMessages())) {
            messages.addAll(param.getMessages().stream().filter(Objects::nonNull)
                    .map(record -> {
                        Message message = new Message();
                        message.setRole(record.getMessageType().name().toLowerCase(Locale.ROOT));
                        message.setContent(record.getText());
                        return message;
                    }).toList());
        }
        // 添加用户问题
        Message message = new Message();
        message.setRole(MessageTypeEnum.USER.name().toLowerCase(Locale.ROOT));
        message.setContent(param.getPrompt());
        messages.add(message);

        req.setMessages(messages.toArray(new Message[0]));

        return Flux.create(sink -> {
            try {
                ChatCompletionsResponse resp = hunyuanClient.ChatCompletions(req);
                if (resp.isStream()) {
                    for (SSEResponseModel.SSE e : resp) {
                        ChatCompletionsResponse response = JSONUtil.toBean(e.Data, ChatCompletionsResponse.class);
                        if (log.isDebugEnabled()) {
                            log.debug("ChatCompletionsResponse: {}", response);
                        }
                        StringBuilder content = new StringBuilder();
                        for (Choice choice : response.getChoices()) {
                            content.append(choice.getDelta().getContent());
                        }
                        Usage usage = response.getUsage();
                        ConversationReplyVO replyVO = ConversationReplyVO.builder()
                                .content(content.toString())
                                .tokenUsage(TokenUsageVO.builder()
                                        .promptTokens(usage.getPromptTokens())
                                        .generationTokens(usage.getCompletionTokens())
                                        .totalTokens(usage.getTotalTokens())
                                        .build())
                                .build();
                        sink.next(ResultWrapper.ok(replyVO));
                    }
                    sink.complete();
                } else {
                    log.error("Expected a stream but received a non-stream response.");
                    sink.error(new ServiceException(AIChatErrorCode.CHAT_NON_STREAM_RESPONSE_ERROR));
                }
            } catch (TencentCloudSDKException e) {
                log.error("TencentCloudSDKException : {}", e.toString());
                sink.error(new ServiceException(AIChatErrorCode.CHAT_TENCENT_CALL_ERROR));
            }
        });
    }

    @Override
    protected ChatModel createChatModel(ModelVO model) {
        return null;
    }
}
