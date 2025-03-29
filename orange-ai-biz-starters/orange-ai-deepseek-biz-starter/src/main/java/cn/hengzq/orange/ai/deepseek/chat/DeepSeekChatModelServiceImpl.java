package cn.hengzq.orange.ai.deepseek.chat;

import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.service.AbstractChatModelService;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationReplyVO;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.deepseek.config.DeepSeekStorageProperties;
import cn.hengzq.orange.ai.deepseek.constant.ChatModelEnum;
import cn.hengzq.orange.ai.deepseek.constant.DeepSeekContent;
import cn.hengzq.orange.ai.deepseek.dto.ChatCompletionsParam;
import cn.hengzq.orange.ai.deepseek.dto.ChatCompletionsResponse;
import cn.hengzq.orange.ai.deepseek.dto.MessageItem;
import cn.hengzq.orange.common.constant.SecurityConstant;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.model.ChatModel;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class DeepSeekChatModelServiceImpl extends AbstractChatModelService {

    private final DeepSeekStorageProperties deepSeekStorageProperties;

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.DEEP_SEEK;
    }


    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ChatModelConversationParam param) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.get("application/json");
        ChatCompletionsParam reqBody = generateChatRequestBody(param);

        Request request = new Request.Builder()
                .url(deepSeekStorageProperties.getChatUrl())
                .addHeader(SecurityConstant.AUTHORIZATION, "Bearer " + deepSeekStorageProperties.getToken())
                .post(RequestBody.create(JSONUtil.toJsonStr(reqBody), mediaType))
                .build();


        return Flux.create(emitter -> {
            EventSourceListener listener = new EventSourceListener() {
                @Override
                public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
                    System.out.println("SSE connection opened");
                }

                @Override
                public void onClosed(@NotNull EventSource eventSource) {
                    emitter.complete();
                    System.out.println("SSE connection closed");
                }

                @Override
                public void onEvent(@NotNull EventSource eventSource, String id, String type, @NotNull String data) {
                    if (log.isDebugEnabled()) {
                        log.debug("data:{}", data);
                    }
                    if (StrUtil.isBlank(data) || DeepSeekContent.DONE.equalsIgnoreCase(data)) {
                        emitter.complete();
                        return;
                    }
                    ChatCompletionsResponse response = JSONUtil.toBean(data, ChatCompletionsResponse.class);
                    StringBuilder content = new StringBuilder();
                    for (ChatCompletionsResponse.ChoiceItem choice : response.getChoices()) {
                        content.append(choice.getDelta().getContent());
                    }
                    emitter.next(ResultWrapper.ok(ConversationReplyVO.builder().content(content.toString()).build()));
                }

                @Override
                public void onFailure(@NotNull EventSource eventSource, Throwable t, Response response) {
                    System.err.println("Error occurred: " + t);
                }
            };
            EventSource.Factory factory = EventSources.createFactory(client);
            factory.newEventSource(request, listener);
        });
    }

    private ChatCompletionsParam generateChatRequestBody(ChatModelConversationParam param) {
        List<MessageItem> messages = new ArrayList<>();
        if (CollUtil.isNotEmpty(param.getMessages())) {
            for (Message record : param.getMessages()) {
                if (MessageType.USER.equals(record.getMessageType())) {
                    messages.add(MessageItem.builder().role(MessageType.USER.getValue()).content(record.getText()).build());
                    continue;
                }
                if (MessageType.ASSISTANT.equals(record.getMessageType())) {
                    messages.add(MessageItem.builder().role(MessageType.ASSISTANT.getValue()).content(record.getText()).build());
                }
            }
        }
        messages.add(MessageItem.builder().role(MessageType.USER.getValue()).content(param.getPrompt()).build());

        return ChatCompletionsParam.builder()
                .model(param.getModel().getModelName())
                .messages(messages)
                .stream(Boolean.TRUE)
                .build();
    }

    @Override
    protected ChatModel createChatModel(ModelVO model) {
        return null;
    }

    @Override
    public List<String> listModel() {
        return ChatModelEnum.getModelList();
    }
}
