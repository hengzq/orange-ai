package cn.hengzq.orange.ai.core.biz.chat.service.impl;

import cn.hengzq.orange.ai.common.biz.chat.constant.AIChatErrorCode;
import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelOptions;
import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.ai.common.biz.chat.vo.TokenUsageVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ConversationStreamParam;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionMessageParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionParam;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatService;
import cn.hengzq.orange.ai.core.biz.knowledge.service.KnowledgeBaseService;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.ai.core.biz.session.service.SessionMessageService;
import cn.hengzq.orange.ai.core.biz.session.service.SessionService;
import cn.hengzq.orange.common.exception.ServiceException;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatModelServiceFactory chatModelServiceFactory;

    private final SessionService sessionService;

    private final SessionMessageService sessionMessageService;

    private final ModelService modelService;

    private final KnowledgeBaseService knowledgeBaseService;

    @Override
    public ConversationResponse conversation(ConversationStreamParam param) {
        ModelVO model = getModel(param.getModelId());
        ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(model.getPlatform());
        ChatModelConversationParam chatModelConversationParam = ChatModelConversationParam.builder()
                .modelOptions(ChatModelOptions.builder()
                        .model(model.getModelName())
                        .temperature(param.getTemperature())
                        .baseUrl(model.getBaseUrl())
                        .apiKey(model.getApiKey())
                        .build())
                .prompt(param.getPrompt())
                .build();
        return chatModelService.conversation(chatModelConversationParam);
    }

    @Override
    public Flux<Result<ConversationResponse>> conversationStream(ConversationStreamParam param) {
        ModelVO model = modelService.getById(param.getModelId());
        if (Objects.isNull(model)) {
            return Flux.just(ResultWrapper.fail());
        }
        String sessionId = sessionService.getOrCreateSessionId(param.getSessionId(), AddSessionParam.builder()
                .modelId(model.getId())
                .sessionType(SessionTypeEnum.CHAT_EXPERIENCE)
                .name(param.getPrompt())
                .build());

        ChatModelConversationParam.ChatModelConversationParamBuilder paramBuilder = ChatModelConversationParam.builder()
                .modelOptions(ChatModelOptions.builder()
                        .model(model.getModelName())
                        .temperature(param.getTemperature())
                        .baseUrl(model.getBaseUrl())
                        .apiKey(model.getApiKey())
                        .build())
                .prompt(param.getPrompt())
                .systemPrompt(param.getSystemPrompt());

        // 获取对话上下文记录
//        List<ChatSessionRecordEntity> entityList = chatSessionRecordMapper.selectListBySessionId(sessionId);
//        List<ChatSessionRecordVO> contextMessageList = ChatSessionRecordConverter.INSTANCE.toListVO(entityList);

        String questionId = sessionMessageService.add(AddSessionMessageParam.builder()
                .sessionId(sessionId)
                .role(MessageTypeEnum.USER)
                .content(param.getPrompt())
                .build());

        return stream(paramBuilder.build(), model, sessionId, questionId);
    }

    @Override
    public Flux<Result<ConversationResponse>> stream(ChatModelConversationParam param) {
        ChatModelOptions modelOptions = param.getModelOptions();
        // 1.获取或创建回话
        String sessionId = sessionService.getOrCreateSessionId(param.getSessionId(), AddSessionParam.builder()
                .modelId(modelOptions.getModelId())
                .associationId(param.getSessionAssociationId())
                .sessionType(param.getSessionType())
                .name(param.getPrompt())
                .build());

        // 2. 保存用户问题
        String questionId = sessionMessageService.add(AddSessionMessageParam.builder()
                .sessionId(sessionId)
                .role(MessageTypeEnum.USER)
                .content(param.getPrompt())
                .build());

        ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(modelOptions.getPlatform());
        AtomicReference<StringBuffer> content = new AtomicReference<>(new StringBuffer());
        return chatModelService.conversationStream(param)
                .filter(Objects::nonNull)
                .map(result -> {
                    content.get().append(result.getData().getContent());
                    TokenUsageVO tokenUsage = result.getData().getTokenUsage();
                    if (Objects.nonNull(tokenUsage)) {
//                        userRecord.setTokenQuantity(result.getData().getTokenUsage().getPromptTokens());
//                        assistantRecord.setTokenQuantity(result.getData().getTokenUsage().getGenerationTokens());
                    }
                    // 封装消息ID
                    result.getData().setSessionId(sessionId);
                    return result;
                })
                .doOnComplete(() -> {
                    if (log.isDebugEnabled()) {
                        log.info("Conversation completed. Content: {}", content);
                    }
                }).onErrorResume(error -> {
                    log.error("An error occurred: {}", error.getMessage(), error);
                    content.set(new StringBuffer());
                    content.get().append(AIChatErrorCode.CHAT_CONVERSATION_IS_ERROR.getMsg());
                    return Mono.just(ResultWrapper.fail(AIChatErrorCode.CHAT_CONVERSATION_IS_ERROR));
                }).doFinally(signalType -> {
                    sessionMessageService.add(AddSessionMessageParam.builder()
                            .sessionId(sessionId)
                            .parentId(questionId)
                            .role(MessageTypeEnum.ASSISTANT)
                            .content(content.toString())
                            .build());
                    log.info("Conversation completed.");
                });
    }

    private @NotNull Flux<Result<ConversationResponse>> stream(ChatModelConversationParam param, ModelVO model, String sessionId, String questionId) {
        ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(model.getPlatform());
        AtomicReference<StringBuffer> content = new AtomicReference<>(new StringBuffer());
        return chatModelService.conversationStream(param)
                .filter(Objects::nonNull)
                .map(result -> {
                    content.get().append(result.getData().getContent());
                    TokenUsageVO tokenUsage = result.getData().getTokenUsage();
                    if (Objects.nonNull(tokenUsage)) {
//                        userRecord.setTokenQuantity(result.getData().getTokenUsage().getPromptTokens());
//                        assistantRecord.setTokenQuantity(result.getData().getTokenUsage().getGenerationTokens());
                    }
                    // 封装消息ID
                    result.getData().setSessionId(sessionId);
                    return result;
                })
                .doOnComplete(() -> {
                    if (log.isDebugEnabled()) {
                        log.info("Conversation completed. Content: {}", content);
                    }
                }).onErrorResume(error -> {
                    log.error("An error occurred: {}", error.getMessage(), error);
                    content.set(new StringBuffer());
                    content.get().append(AIChatErrorCode.CHAT_CONVERSATION_IS_ERROR.getMsg());
                    return Mono.just(ResultWrapper.fail(AIChatErrorCode.CHAT_CONVERSATION_IS_ERROR));
                }).doFinally(signalType -> {
                    sessionMessageService.add(AddSessionMessageParam.builder()
                            .sessionId(sessionId)
                            .parentId(questionId)
                            .role(MessageTypeEnum.ASSISTANT)
                            .content(content.toString())
                            .build());
                    log.info("Conversation completed.");
                });
    }

    private @NotNull ModelVO getModel(String modelId) {
        ModelVO model = modelService.getById(modelId);
        if (Objects.isNull(model)) {
            log.error("模型不存在 modelId: {}", modelId);
            throw new ServiceException(AIModelErrorCode.MODEL_DATA_NOT_EXIST);
        }
        return model;
    }

}
