package cn.hengzq.orange.ai.common.biz.chat.service;

import cn.hengzq.orange.ai.common.biz.chat.constant.ChatErrorCode;
import cn.hengzq.orange.ai.common.biz.chat.constant.ConverstationEventEnum;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelOptions;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.ai.common.biz.mcp.vo.McpServerVO;
import cn.hengzq.orange.common.exception.ServiceException;
import cn.hengzq.orange.common.response.ApiResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.vectorstore.VectorStore;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class AbstractChatModelService implements ChatModelService {

    /**
     * ChatModel缓存
     * key：密钥API_KEY
     * value：基于KEY创建的ChatModel
     */
    protected static final Cache<String, ChatModel> chatModelMap = CacheUtil.newLFUCache(100);

    /**
     * 创建ChatModel
     *
     * @param model   模型
     * @param baseUrl 模型基础URL
     * @param apiKey  模型密钥
     * @return 返回 ChatModel
     */
    protected abstract ChatModel createChatModel(String model, String baseUrl, String apiKey);

    /**
     * 创建ChatOptions
     */
    protected abstract ChatOptions createChatOptions(ChatModelOptions param);


    @Override
    public ChatModel getOrCreateChatModel(String model, String baseUrl, String apiKey) {
        if (chatModelMap.containsKey(apiKey)) {
            return chatModelMap.get(apiKey);
        }
        ChatModel chatModel = createChatModel(model, baseUrl, apiKey);
        chatModelMap.put(apiKey, chatModel);
        return chatModel;
    }

    @Override
    public Flux<Result<ConversationResponse>> stream(ChatModelConversationParam param) {
        List<Message> messages = CollUtil.isEmpty(param.getMessages()) ? new ArrayList<>() : new ArrayList<>(param.getMessages());
        messages.add(new UserMessage(param.getPrompt()));

        ChatModelOptions options = param.getModelOptions();
        if (Objects.isNull(options)) {
            log.warn("options is null");
            throw new ServiceException(ChatErrorCode.CHAT_SESSION_TYPE_IS_ERROR);
        }
        Prompt prompt = new Prompt(messages, createChatOptions(options));
        ChatModel chatModel = this.getOrCreateChatModel(options.getModel(), options.getBaseUrl(), options.getApiKey());

        ChatClient.Builder chatClientBuilder = ChatClient.builder(chatModel);
        // 设置系统提示词
        if (StrUtil.isNotBlank(param.getSystemPrompt())) {
            chatClientBuilder.defaultSystem(param.getSystemPrompt());
        }

        // 使用知识库
//        if (CollUtil.isNotEmpty(param.getBaseList())) {
//            List<Advisor> advisors = new ArrayList<>();
//            for (BaseVO base : param.getBaseList()) {
//                ModelVO embeddingModel = base.getEmbeddingModel();
//                EmbeddingModelService embeddingModelService = embeddingModelServiceFactory.getEmbeddingModelService(embeddingModel.getPlatform());
//                VectorStore vectorStore = vectorStoreService.getOrCreateVectorStore(base.getVectorCollectionName(), embeddingModelService.getOrCreateEmbeddingModel(embeddingModel));
//                RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
//                        .documentRetriever(VectorStoreDocumentRetriever.builder()
//                                .vectorStore(vectorStore)
//                                .build())
//                        .build();
//                advisors.add(advisor);
//            }
//            chatClientBuilder.defaultAdvisors(advisors);
//        }
        if (CollUtil.isNotEmpty(param.getVectorStores())) {
            List<Advisor> advisors = new ArrayList<>();
            for (VectorStore vectorStore : param.getVectorStores()) {
                RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
                        .documentRetriever(VectorStoreDocumentRetriever.builder()
                                .vectorStore(vectorStore)
                                .build())
                        .build();
                advisors.add(advisor);
            }
            chatClientBuilder.defaultAdvisors(advisors);
        }


        // 加载 MCP服务
        if (CollUtil.isNotEmpty(param.getMcpServerList())) {
            List<ToolCallback> callbacks = getToolCallbacksFromSyncClients(getMcpSyncClients(param.getMcpServerList()));
            if (CollUtil.isNotEmpty(callbacks)) {
                chatClientBuilder.defaultToolCallbacks(callbacks);
            } else {
                log.warn("clients is null");
            }
        }

        ChatClient.StreamResponseSpec stream = chatClientBuilder.build()
                .prompt(prompt)
                .stream();
        return stream
                .chatResponse()
                .takeWhile(chatResponse -> Objects.nonNull(chatResponse) && Objects.nonNull(chatResponse.getResult())
                        && Objects.nonNull(chatResponse.getResult().getOutput()))
                .map(chatResponse -> {
                    if (log.isDebugEnabled()) {
                        log.debug("chatResponse: {}", chatResponse);
                    }
                    Usage usage = chatResponse.getMetadata().getUsage();
                    String content = chatResponse.getResult().getOutput().getText();
                    String finishReason = chatResponse.getResult().getMetadata().getFinishReason();

                    ConversationResponse replyVO = ConversationResponse.builder()
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
    public Flux<ApiResponse<ConversationResponse>> stream(ChatParam param) {
        List<Message> messages = CollUtil.isEmpty(param.getMessages()) ? new ArrayList<>() : new ArrayList<>(param.getMessages());
        messages.add(new UserMessage(param.getPrompt()));

        ChatModelOptions options = param.getModelOptions();
        if (Objects.isNull(options)) {
            log.warn("options is null");
            throw new ServiceException(ChatErrorCode.CHAT_SESSION_TYPE_IS_ERROR);
        }
        Prompt prompt = new Prompt(messages, createChatOptions(options));
        ChatModel chatModel = this.getOrCreateChatModel(options.getModel(), options.getBaseUrl(), options.getApiKey());

        ChatClient.Builder chatClientBuilder = ChatClient.builder(chatModel);
        // 设置系统提示词
        if (StrUtil.isNotBlank(param.getSystemPrompt())) {
            chatClientBuilder.defaultSystem(param.getSystemPrompt());
        }

        if (CollUtil.isNotEmpty(param.getVectorStores())) {
            List<Advisor> advisors = new ArrayList<>();
            for (VectorStore vectorStore : param.getVectorStores()) {
                RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
                        .documentRetriever(VectorStoreDocumentRetriever.builder()
                                .vectorStore(vectorStore)
                                .build())
                        // 对生成的查询进行上下文增强
                        .queryAugmenter(ContextualQueryAugmenter.builder()
                                .allowEmptyContext(true)
                                .build())
                        .build();
                advisors.add(advisor);
            }
            chatClientBuilder.defaultAdvisors(advisors);
        }

        if (CollUtil.isNotEmpty(param.getCallbacks())) {
            chatClientBuilder.defaultToolCallbacks(param.getCallbacks());
        }
        // 集成工作流服务
//        chatClientBuilder.defaultToolCallbacks(Arrays.asList(ToolCallbacks.from(new WorkflowIntegrationService())));

        ChatClient.StreamResponseSpec stream = chatClientBuilder.build()
                .prompt(prompt)
                .stream();
        return stream
                .chatResponse()
                .takeWhile(chatResponse -> Objects.nonNull(chatResponse) && Objects.nonNull(chatResponse.getResult())
                        && Objects.nonNull(chatResponse.getResult().getOutput()))
                .map(chatResponse -> {
                    if (log.isDebugEnabled()) {
                        log.debug("chatResponse: {}", chatResponse);
                    }
                    Usage usage = chatResponse.getMetadata().getUsage();
                    String content = chatResponse.getResult().getOutput().getText();
                    String finishReason = chatResponse.getResult().getMetadata().getFinishReason();

                    ConversationResponse replyVO = ConversationResponse.builder()
                            .event("STOP".equalsIgnoreCase(finishReason) ? ConverstationEventEnum.FINISHED : ConverstationEventEnum.REPLY)
                            .content(content)
//                            .tokenUsage(TokenUsageVO.builder()
//                                    .promptTokens(usage.getPromptTokens())
//                                    .generationTokens(usage.getGenerationTokens())
//                                    .totalTokens(usage.getTotalTokens())
//                                    .build())
                            .build();
                    return ApiResponse.ok(replyVO);
                });
    }

    @Override
    public ConversationResponse conversation(ChatModelConversationParam param) {
        ChatModelOptions options = param.getModelOptions();
        ChatModel chatModel = this.getOrCreateChatModel(options.getModel(), options.getBaseUrl(), options.getApiKey());
        String message = chatModel.call(param.getPrompt());
        return ConversationResponse.builder()
                .event(ConverstationEventEnum.FINISHED)
                .content(message)
                .build();
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
