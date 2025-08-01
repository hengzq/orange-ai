package cn.hengzq.orange.ai.core.biz.chat.service.impl;

import cn.hengzq.orange.ai.common.biz.chat.constant.ChatErrorCode;
import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelOptions;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatParam;
import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.ai.common.biz.chat.vo.TokenUsageVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ChatConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ConversationStreamParam;
import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.BaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBaseListParam;
import cn.hengzq.orange.ai.common.biz.mcp.vo.McpServerVO;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.McpServerListParam;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import cn.hengzq.orange.ai.common.biz.session.vo.SessionMessageVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionMessageParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionMessageListParam;
import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatService;
import cn.hengzq.orange.ai.core.biz.embedding.service.EmbeddingModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.knowledge.service.BaseService;
import cn.hengzq.orange.ai.core.biz.mcp.service.McpServerService;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.ai.core.biz.session.converter.SessionMessageConverter;
import cn.hengzq.orange.ai.core.biz.session.service.SessionMessageService;
import cn.hengzq.orange.ai.core.biz.session.service.SessionService;
import cn.hengzq.orange.ai.core.biz.vectorstore.service.VectorStoreServiceFactory;
import cn.hengzq.orange.common.exception.ServiceException;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatModelServiceFactory chatModelServiceFactory;

    private final EmbeddingModelServiceFactory embeddingModelServiceFactory;

    private final VectorStoreServiceFactory vectorStoreServiceFactory;

    private final SessionService sessionService;

    private final SessionMessageService sessionMessageService;

    private final ModelService modelService;

    private final BaseService baseService;

    private final McpServerService mcpServerService;


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
    public Flux<Result<ConversationResponse>> conversationStream(ChatConversationParam param) {
        ChatParam chatParam = ChatParam.builder()
                .prompt(param.getPrompt())
                .systemPrompt(param.getSystemPrompt())
                .build();

        // 1.获取或创建回话
        String sessionId = sessionService.getOrCreateSessionId(param.getSessionId(), AddSessionParam.builder()
                .modelId(param.getModelId())
                .associationId(param.getSessionAssociationId())
                .sessionType(param.getSessionType())
                .name(param.getPrompt())
                .build());

        // 2. 加载历史会话信息
        List<SessionMessageVO> messages = sessionMessageService.list(SessionMessageListParam.builder().sessionId(sessionId).build());
        if (CollUtil.isNotEmpty(messages)) {
            chatParam.setMessages(SessionMessageConverter.INSTANCE.toMessageList(messages));
        }
        // 3. 保存用户问题
        String questionId = sessionMessageService.add(AddSessionMessageParam.builder()
                .sessionId(sessionId)
                .role(MessageTypeEnum.USER)
                .content(param.getPrompt())
                .build());

        AtomicReference<StringBuffer> content = new AtomicReference<>(new StringBuffer());

        return Flux.defer(() -> {
                    // 加载模型
                    ChatModelOptions chatModelOptions = getChatModelOptions(param.getModelId());
                    chatParam.setModelOptions(chatModelOptions);

                    // 知识库加载
                    chatParam.setVectorStores(getVectorStore(param.getBaseIds()));

                    // 加载MCP 服务
                    chatParam.setCallbacks(getCallbacks(param.getMcpIds()));

                    ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(chatModelOptions.getPlatform());
                    return chatModelService.stream(chatParam);
                }).map(result -> {
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
                    if (error instanceof ServiceException se) {
                        content.get().append(se.getMessage());
                        return Mono.just(ResultWrapper.fail(se.getErrorCode()));
                    }
                    content.get().append(ChatErrorCode.CHAT_CONVERSATION_IS_ERROR.getMsg());
                    return Mono.just(ResultWrapper.fail(ChatErrorCode.CHAT_CONVERSATION_IS_ERROR));
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

    private List<ToolCallback> getCallbacks(List<String> mcpIds) {
        if (CollUtil.isEmpty(mcpIds)) {
            return Collections.emptyList();
        }
        List<McpServerVO> mcpServerList = mcpServerService.list(McpServerListParam.builder().ids(mcpIds).build());
        if (CollUtil.isEmpty(mcpServerList)) {
            return Collections.emptyList();
        }
        return getToolCallbacksFromSyncClients(getMcpSyncClients(mcpServerList));
    }

    private List<VectorStore> getVectorStore(List<String> baseIds) {
        if (CollUtil.isEmpty(baseIds)) {
            return Collections.emptyList();
        }
        List<BaseVO> baseList = baseService.list(KnowledgeBaseListParam.builder().ids(baseIds).build());
        if (CollUtil.isEmpty(baseList)) {
            return Collections.emptyList();
        }

        List<VectorStore> vectorStores = new ArrayList<>();
        for (BaseVO base : baseList) {
            ModelVO embeddingModel = base.getEmbeddingModel();
            EmbeddingModelService embeddingModelService = embeddingModelServiceFactory.getEmbeddingModelService(embeddingModel.getPlatform());
            VectorStoreService vectorStoreService = vectorStoreServiceFactory.getVectorStoreService(VectorDatabaseEnum.MILVUS);
            VectorStore vectorStore = vectorStoreService.getOrCreateVectorStore(base.getVectorCollectionName(), embeddingModelService.getOrCreateEmbeddingModel(embeddingModel));
            vectorStores.add(vectorStore);
        }
        return vectorStores;
    }

    private ChatModelOptions getChatModelOptions(String modelId) {
        if (StrUtil.isBlank(modelId)) {
            throw new ServiceException(ChatErrorCode.CHAT_NO_CONFIG_MODEL_ERROR);
        }
        ModelVO model = modelService.getById(modelId);
        if (Objects.isNull(model)) {
            throw new ServiceException(ChatErrorCode.CHAT_NO_CONFIG_MODEL_ERROR);
        }
        return ChatModelOptions.builder()
                .modelId(model.getId())
                .model(model.getModelName())
                .platform(model.getPlatform())
                .apiKey(model.getApiKey())
                .baseUrl(model.getBaseUrl())
                .build();
    }

    private @NotNull Flux<Result<ConversationResponse>> stream(ChatModelConversationParam param, ModelVO model, String sessionId, String questionId) {
        ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(model.getPlatform());
        AtomicReference<StringBuffer> content = new AtomicReference<>(new StringBuffer());
        return chatModelService.stream(param)
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
                    content.get().append(ChatErrorCode.CHAT_CONVERSATION_IS_ERROR.getMsg());
                    return Mono.just(ResultWrapper.fail(ChatErrorCode.CHAT_CONVERSATION_IS_ERROR));
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


    private final static Cache<String, McpSyncClient> MCP_SYNC_CLIENT_CACHE = CacheUtil.newLFUCache(100);

    private List<ToolCallback> getToolCallbacksFromSyncClients(List<McpSyncClient> clients) {
        if (CollUtil.isEmpty(clients)) {
            return List.of();
        }
        try {
            return McpToolUtils.getToolCallbacksFromSyncClients(clients);
        } catch (Exception e) {
            log.error("getToolCallbacksFromSyncClients error", e);
            return List.of();
        }
    }

    public List<McpSyncClient> getMcpSyncClients(List<McpServerVO> mcpServerList) {
        if (CollUtil.isEmpty(mcpServerList)) {
            return List.of();
        }
        List<McpSyncClient> clients = new ArrayList<>();
        for (McpServerVO vo : mcpServerList) {
            if (Objects.isNull(vo) || Objects.isNull(vo.getConnectionUrl())) {
                continue;
            }
            if (MCP_SYNC_CLIENT_CACHE.containsKey(vo.getId())) {
                clients.add(MCP_SYNC_CLIENT_CACHE.get(vo.getId()));
                continue;
            }
            try {
                McpSchema.Implementation clientInfo = new McpSchema.Implementation(
                        vo.getName(), "1.0.0");
                HttpClientSseClientTransport transport = HttpClientSseClientTransport.builder(vo.getConnectionUrl()).sseEndpoint(vo.getSseEndpoint()).build();
                McpSyncClient client = McpClient.sync(transport).clientInfo(clientInfo).build();
                client.initialize();
                MCP_SYNC_CLIENT_CACHE.put(vo.getId(), client);
                clients.add(client);
            } catch (Exception e) {
                log.error("McpSyncClient error. mcp server name: {}", vo.getName(), e);
            }
        }
        return clients;
    }

}
