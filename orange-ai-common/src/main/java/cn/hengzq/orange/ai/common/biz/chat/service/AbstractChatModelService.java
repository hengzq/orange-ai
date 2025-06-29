package cn.hengzq.orange.ai.common.biz.chat.service;

import cn.hengzq.orange.ai.common.biz.chat.constant.AIChatErrorCode;
import cn.hengzq.orange.ai.common.biz.chat.constant.ConverstationEventEnum;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelOptions;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.common.exception.ServiceException;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
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
     */
    protected abstract ChatModel createChatModel(ModelVO model);

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


    /**
     * 获取或创建聊天模型。
     *
     * @param model 需要获取或创建的模型对象。
     * @return 如果模型已存在，返回对应的聊天模型；否则，创建新的聊天模型并返回。
     */
    @Override
    public ChatModel getOrCreateChatModel(ModelVO model) {
        if (chatModelMap.containsKey(model.getApiKey())) {
            return chatModelMap.get(model.getApiKey());
        }
        ChatModel chatModel = createChatModel(model);
        chatModelMap.put(model.getApiKey(), chatModel);
        return chatModel;
    }

    @Override
    public ChatModel getOrCreateChatModel(String model, String baseUrl, String apiKey) {
        if (chatModelMap.containsKey(apiKey)) {
            return chatModelMap.get(apiKey);
        }
        ChatModel chatModel = createChatModel(model, baseUrl, apiKey);
        chatModelMap.put(apiKey, chatModel);
        return chatModel;
    }

    public ChatOptions getOrCreateChatOptions(ChatModelConversationParam param) {
        ChatModelOptions options = param.getOptions();
        if (Objects.isNull(options)) {
            log.warn("options is null");
            throw new ServiceException(AIChatErrorCode.CHAT_SESSION_TYPE_IS_ERROR);
        }
        return createChatOptions(options);
    }

    @Override
    public Flux<Result<ConversationResponse>> conversationStream(ChatModelConversationParam param) {
        List<Message> messages = CollUtil.isEmpty(param.getMessages()) ? new ArrayList<>() : new ArrayList<>(param.getMessages());
        messages.add(new UserMessage(param.getPrompt()));

        Prompt prompt = new Prompt(messages, this.getOrCreateChatOptions(param));
        ChatModel chatModel = this.getOrCreateChatModel(param.getModel());

//        McpSchema.Implementation clientInfo = new McpSchema.Implementation(
//                "amap-amap-sse", "1.0.0");
//
//        HttpClientSseClientTransport transport = HttpClientSseClientTransport.builder("https://mcp.amap.com")
//                .sseEndpoint("/sse?key=")
//                .clientBuilder(HttpClient.newBuilder())
//                .objectMapper(new ObjectMapper())
//                .build();
//
//        McpSyncClient client = McpClient.sync(transport).clientInfo(clientInfo).build();
//        client.initialize();

        ChatClient.Builder chatClientBuilder = ChatClient.builder(chatModel);
//                .defaultTools(McpToolUtils.getToolCallbacksFromSyncClients(List.of(client)));
        // 设置系统提示词
        if (StrUtil.isNotBlank(param.getSystemPrompt())) {
            chatClientBuilder.defaultSystem(param.getSystemPrompt());
        }

        // 使用知识库
        if (CollUtil.isNotEmpty(param.getBaseList())) {
//            List<Advisor> advisors = new ArrayList<>();
//            for (KnowledgeBaseVO base : param.getBaseList()) {
//                ModelVO embeddingModel = base.getEmbeddingModel();
//                EmbeddingModelService embeddingModelService = embeddingModelServiceFactory.getEmbeddingModelService(embeddingModel.getPlatform());
//                VectorStore vectorStore = vectorStoreService.getOrCreateVectorStore(base.getVectorCollectionName(), embeddingModelService.getOrCreateEmbeddingModel(embeddingModel));
//
//                QuestionAnswerAdvisor advisor = new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder()
//                        .query(context.getPrompt()).topK(5).build());
//                advisors.add(advisor);
//            }
//            chatClientBuilder.defaultAdvisors(context.getAdvisors());
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
    public ConversationResponse conversation(ChatModelConversationParam param) {
        ChatModel chatModel = this.getOrCreateChatModel(param.getModel());
        String message = chatModel.call(param.getPrompt());
        return ConversationResponse.builder()
                .event(ConverstationEventEnum.FINISHED)
                .content(message)
                .build();
    }
}
