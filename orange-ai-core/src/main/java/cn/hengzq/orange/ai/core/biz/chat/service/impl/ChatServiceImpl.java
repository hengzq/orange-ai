package cn.hengzq.orange.ai.core.biz.chat.service.impl;

import cn.hengzq.orange.ai.common.biz.agent.vo.AgentVO;
import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.ai.common.biz.chat.vo.TokenUsageVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.AgentConversationStreamParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ConversationStreamParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeBaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBaseListParam;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionMessageParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionParam;
import cn.hengzq.orange.ai.core.biz.agent.service.AgentService;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatService;
import cn.hengzq.orange.ai.core.biz.knowledge.service.KnowledgeBaseService;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.ai.core.biz.session.service.SessionMessageService;
import cn.hengzq.orange.ai.core.biz.session.service.SessionService;
import cn.hengzq.orange.common.exception.ServiceException;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatModelServiceFactory chatModelServiceFactory;

    private final SessionService sessionService;

    private final SessionMessageService sessionMessageService;

    private final ModelService modelService;

    private final AgentService agentService;

    private final KnowledgeBaseService knowledgeBaseService;

    @Override
    public ConversationResponse conversation(ConversationStreamParam param) {
        return null;
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
        ChatModelConversationParam.ChatModelConversationParamBuilder paramBuilder = ChatModelConversationParam.builder().model(model).prompt(param.getPrompt());


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
    public Flux<Result<ConversationResponse>> agentConversationStream(AgentConversationStreamParam param) {
        // 1. 加载Agent
        AgentVO agent = getAgent(param.getAgentId());
        // 2. 加载模型
        ModelVO model = getModel(agent.getModelId());

        // 构建对话参数
        ChatModelConversationParam.ChatModelConversationParamBuilder paramBuilder = ChatModelConversationParam.builder().model(model).prompt(param.getPrompt());

        // 3. 创建或者获取已有的会话
        String sessionId = sessionService.getOrCreateSessionId(param.getSessionId(), AddSessionParam.builder()
                .modelId(model.getId())
                .associationId(param.getAgentId())
                .sessionType(SessionTypeEnum.AGENT)
                .name(param.getPrompt())
                .build());

        //  4. 保存用户问题
        String questionId = sessionMessageService.add(AddSessionMessageParam.builder()
                .sessionId(sessionId)
                .role(MessageTypeEnum.USER)
                .content(param.getPrompt())
                .build());

        // 5. 加载知识库
        List<String> baseIds = agent.getBaseIds();
        if (CollUtil.isNotEmpty(baseIds)) {
            List<KnowledgeBaseVO> baseList = knowledgeBaseService.list(KnowledgeBaseListParam.builder().ids(baseIds).build());
            if (CollUtil.isNotEmpty(baseList)) {
                paramBuilder.baseList(baseList);
            }
        }

        return stream(paramBuilder.build(), model, sessionId, questionId);
    }

    private @NotNull Flux<Result<ConversationResponse>> stream(ChatModelConversationParam param, ModelVO model, String sessionId, String questionId) {
        ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(model.getPlatform());
        StringBuilder content = new StringBuilder();
        return chatModelService.conversationStream(param)
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

    private @NotNull ModelVO getModel(String modelId) {
        ModelVO model = modelService.getById(modelId);
        if (Objects.isNull(model)) {
            log.error("模型不存在 modelId: {}", modelId);
            throw new ServiceException(AIModelErrorCode.MODEL_DATA_NOT_EXIST);
        }
        return model;
    }

    private @NotNull AgentVO getAgent(String agentId) {
        AgentVO agent = agentService.getById(agentId);
        if (Objects.isNull(agent)) {
            log.error("智能体不存在 modelId: {}", agentId);
            throw new ServiceException(AIModelErrorCode.MODEL_DATA_NOT_EXIST);
        }
        return agent;
    }

}
