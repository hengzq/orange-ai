package cn.hengzq.orange.ai.core.biz.chat.service.impl;

import cn.hengzq.orange.ai.common.biz.chat.constant.AIChatErrorCode;
import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationReplyVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.TokenUsageVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.CompletionsParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ConversationParam;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import cn.hengzq.orange.ai.common.biz.session.vo.SessionVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionMessageParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionParam;
import cn.hengzq.orange.ai.core.biz.chat.handler.ChatContext;
import cn.hengzq.orange.ai.core.biz.chat.handler.ChatHandler;
import cn.hengzq.orange.ai.core.biz.chat.handler.impl.*;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatService;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.ai.core.biz.session.service.SessionMessageService;
import cn.hengzq.orange.ai.core.biz.session.service.SessionService;
import cn.hengzq.orange.common.constant.GlobalConstant;
import cn.hengzq.orange.common.exception.ServiceException;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatModelServiceFactory chatModelServiceFactory;

    private final SessionService sessionService;

    private final SessionMessageService sessionMessageService;

    private final ModelService modelService;


    @Override
    public ConversationReplyVO conversation(ConversationParam param) {
        return null;
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param) {
        ModelVO model = modelService.getById(param.getModelId());
        if (Objects.isNull(model)) {
            return Flux.just(ResultWrapper.fail());
        }
        String sessionId = getOrCreateSessionId(param.getSessionId(), param.getPrompt(), param.getModelId());

        // 获取对话上下文记录
//        List<ChatSessionRecordEntity> entityList = chatSessionRecordMapper.selectListBySessionId(sessionId);
//        List<ChatSessionRecordVO> contextMessageList = ChatSessionRecordConverter.INSTANCE.toListVO(entityList);

        String questionId = sessionMessageService.add(AddSessionMessageParam.builder()
                .sessionId(sessionId)
                .parentId(GlobalConstant.DEFAULT_PARENT_ID)
                .role(MessageTypeEnum.USER)
                .content(param.getPrompt())
                .build());

        ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(model.getPlatform());
        StringBuilder content = new StringBuilder();
        return chatModelService.conversationStream(ChatModelConversationParam.builder().model(model).prompt(param.getPrompt()).build())
                .filter(Objects::nonNull)
                .map(result -> {
                    content.append(result.getData().getContent());
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
                    sessionMessageService.add(AddSessionMessageParam.builder()
                            .sessionId(sessionId)
                            .parentId(questionId)
                            .role(MessageTypeEnum.ASSISTANT)
                            .content(content.toString())
                            .build());
                }).onErrorResume(error -> {
                    log.error("An error occurred: {}", error.getMessage(), error);
                    return Mono.just(ResultWrapper.fail());
                });
    }

    private final LoadModelHandler loadModelHandler;

    private final LoadAgentHandler loadAgentHandler;

    private final InvokeChatModelHandler invokeChatModelHandler;

    private final GetOrCreateSessionHandler getOrCreateSessionHandler;

    private final SaveUserSessionMessageHandler saveUserSessionMessageHandler;

    private final ParamCheckHandler paramCheckHandler;

    private final LoadKnowledgeBaseHandler loadKnowledgeBaseHandler;

    @Override
    public SseEmitter completions(CompletionsParam param) {
        SseEmitter emitter = new SseEmitter();
        try {
            if (Objects.isNull(param.getSessionType())) {
                throw new ServiceException(AIChatErrorCode.CHAT_SESSION_TYPE_CANNOT_NULL);
            }
            if (!SessionTypeEnum.include(param.getSessionType())) {
                throw new ServiceException(AIChatErrorCode.CHAT_SESSION_TYPE_IS_ERROR);
            }

            ChatHandler handler = getChatHandlerBySessionType(param.getSessionType());

            ChatContext chatContext = ChatContext.builder()
                    .sessionType(param.getSessionType())
                    .agentId(param.getAgentId())
                    .prompt(param.getPrompt())
                    .emitter(emitter).build();
            handler.handle(chatContext);
        } catch (ServiceException e) {
            try {
                emitter.send(ResultWrapper.fail(e.getErrorCode()));
                emitter.complete();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage(), e);
            emitter.completeWithError(e);
        }
        return emitter;
    }

    private ChatHandler getChatHandlerBySessionType(SessionTypeEnum sessionType) {
        if (SessionTypeEnum.AGENT.equals(sessionType)) {
            paramCheckHandler.next(loadAgentHandler)
                    .next(loadModelHandler)
                    .next(getOrCreateSessionHandler)
                    .next(saveUserSessionMessageHandler)
                    .next(loadKnowledgeBaseHandler)
                    .next(invokeChatModelHandler);
            return paramCheckHandler;
        }
        return null;
    }

    private String getOrCreateSessionId(String sessionId, String prompt, String modelId) {
        if (Objects.isNull(sessionId)) {
            return sessionService.add(AddSessionParam.builder().modelId(modelId).sessionType(SessionTypeEnum.CHAT_EXPERIENCE).name(prompt).build());
        }
        SessionVO session = sessionService.getById(sessionId);
        if (Objects.nonNull(session)) {
            return session.getId();
        }
        return sessionService.add(AddSessionParam.builder().modelId(modelId).sessionType(SessionTypeEnum.CHAT_EXPERIENCE).name(prompt).build());
    }
}
