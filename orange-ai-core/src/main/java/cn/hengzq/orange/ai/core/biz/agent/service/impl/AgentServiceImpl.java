package cn.hengzq.orange.ai.core.biz.agent.service.impl;

import cn.hengzq.orange.ai.common.biz.agent.vo.AgentVO;
import cn.hengzq.orange.ai.common.biz.agent.vo.ConversationStreamVO;
import cn.hengzq.orange.ai.common.biz.agent.vo.param.*;
import cn.hengzq.orange.ai.common.biz.chat.constant.ConverstationEventEnum;
import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeBaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBaseListParam;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import cn.hengzq.orange.ai.common.biz.session.vo.SessionVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionMessageParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionParam;
import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import cn.hengzq.orange.ai.core.biz.agent.converter.AgentConverter;
import cn.hengzq.orange.ai.core.biz.agent.entity.AgentEntity;
import cn.hengzq.orange.ai.core.biz.agent.mapper.AgentMapper;
import cn.hengzq.orange.ai.core.biz.agent.service.AgentService;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.embedding.service.EmbeddingModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.knowledge.service.KnowledgeBaseService;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.ai.core.biz.session.service.SessionMessageService;
import cn.hengzq.orange.ai.core.biz.session.service.SessionService;
import cn.hengzq.orange.ai.core.biz.vectorstore.service.VectorStoreServiceFactory;
import cn.hengzq.orange.common.constant.GlobalConstant;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.mybatis.entity.BaseEntity;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.RequestResponseAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentMapper agentMapper;

    private final ChatModelServiceFactory chatModelServiceFactory;

    private final SessionService sessionService;

    private final SessionMessageService sessionMessageService;

    private final ModelService modelService;
    private final VectorStoreServiceFactory vectorStoreServiceFactory;

    private final KnowledgeBaseService knowledgeBaseService;

    private final EmbeddingModelServiceFactory embeddingModelServiceFactory;
    private final static String DEFAULT_SYSTEM_PROMPT = "你好！我是小橙子，一款基于大语言模型与检索增强生成（RAG）技术的智能问答助手。我能够根据您提供的知识库内容，快速理解上下文，并为您提供精准、专业且详细的解答。无论是复杂问题还是具体需求，我都会尽力帮助您找到最佳答案。请随时告诉我您的问题，我将全力以赴为您服务！";


    @Override
    public Boolean removeById(String id) {
        AgentEntity entity = agentMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return Boolean.TRUE;
        }
        agentMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public String add(AddAgentParam request) {
        AgentEntity entity = AgentConverter.INSTANCE.toEntity(request);
        return agentMapper.insertOne(entity);
    }

    @Override
    public Boolean updateById(String id, UpdateAgentParam request) {
        AgentEntity entity = agentMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity = AgentConverter.INSTANCE.toUpdateEntity(entity, request);
        return agentMapper.updateOneById(entity);
    }

    @Override
    public AgentVO getById(String id) {
        AgentVO vo = AgentConverter.INSTANCE.toVO(agentMapper.selectById(id));
        Map<String, KnowledgeBaseVO> baseMap = knowledgeBaseService.mapKnowledgeBaseByIds(List.of(id));
        if (CollUtil.isNotEmpty(vo.getBaseIds())) {
            List<KnowledgeBaseVO> baseList = new ArrayList<>();
            vo.getBaseIds().forEach(baseId -> {
                if (baseMap.containsKey(baseId)) {
                    baseList.add(baseMap.get(baseId));
                }
            });
            vo.setBaseList(baseList);
        }
        return vo;
    }

    @Override
    public List<AgentVO> list(AgentListParam param) {
        List<AgentEntity> entityList = agentMapper.selectList(
                CommonWrappers.<AgentEntity>lambdaQuery()
                        .likeIfPresent(AgentEntity::getName, param.getName())
                        .orderByDesc(BaseEntity::getCreatedAt)
        );
        return assembleList(AgentConverter.INSTANCE.toListV0(entityList));
    }

    @Override
    public PageDTO<AgentVO> page(AgentPageParam param) {
        PageDTO<AgentEntity> page = agentMapper.selectPage(param, CommonWrappers.<AgentEntity>lambdaQuery()
                .eqIfPresent(AgentEntity::getModelId, param.getModelId())
                .likeIfPresent(AgentEntity::getName, param.getName())
                .orderByDesc(BaseEntity::getCreatedAt)
        );
        PageDTO<AgentVO> pageResult = AgentConverter.INSTANCE.toPage(page);
        if (CollUtil.isEmpty(pageResult.getRecords())) {
            return pageResult;
        }
        pageResult.setRecords(assembleList(pageResult.getRecords()));
        return pageResult;
    }

    private List<AgentVO> assembleList(List<AgentVO> records) {
        if (CollUtil.isEmpty(records)) {
            return records;
        }
        // 组装知识库列表
        List<String> baseIds = new ArrayList<>();
        records.forEach(item -> baseIds.addAll(item.getBaseIds()));
        Map<String, KnowledgeBaseVO> baseMap = knowledgeBaseService.mapKnowledgeBaseByIds(baseIds);
        records.forEach(item -> {
            List<KnowledgeBaseVO> baseList = new ArrayList<>();
            if (CollUtil.isNotEmpty(item.getBaseIds())) {
                item.getBaseIds().forEach(id -> {
                    if (baseMap.containsKey(id)) {
                        baseList.add(baseMap.get(id));
                    }
                });
            }
            item.setBaseList(baseList);
        });
        return records;

    }

    @Override
    public Flux<Result<ConversationStreamVO>> debugConversationStream(AgentDebugConversationParam param) {
        // 查询模型
        ModelVO model = modelService.getById(param.getModelId());
        // 查询知识库
        List<KnowledgeBaseVO> baseList = knowledgeBaseService.list(KnowledgeBaseListParam.builder().ids(param.getBaseIds()).build());

        String sessionId = getOrCreateSessionId(param.getSessionId(), param.getPrompt(), param.getModelId(), SessionTypeEnum.AGENT_DEBUG, null);
        return conversation(sessionId, model, param.getPrompt(), param.getSystemPrompt(), baseList);
    }


    private Flux<Result<ConversationStreamVO>> conversation(String sessionId, ModelVO model, String userPrompt, String systemPrompt, List<KnowledgeBaseVO> baseList) {
        String questionId = sessionMessageService.add(AddSessionMessageParam.builder()
                .parentId(GlobalConstant.DEFAULT_PARENT_ID)
                .sessionId(sessionId)
                .role(MessageTypeEnum.USER)
                .content(userPrompt)
                .build());

        ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(model.getPlatform());
        ChatModel chatModel = chatModelService.getOrCreateChatModel(model);

        VectorStoreService vectorStoreService = vectorStoreServiceFactory.getVectorStoreService(VectorDatabaseEnum.MILVUS);

        List<RequestResponseAdvisor> advisors = new ArrayList<>();
        if (CollUtil.isNotEmpty(baseList)) {
            for (KnowledgeBaseVO base : baseList) {
                ModelVO embeddingModel = base.getEmbeddingModel();
                EmbeddingModelService embeddingModelService = embeddingModelServiceFactory.getEmbeddingModelService(embeddingModel.getPlatform());
                VectorStore vectorStore = vectorStoreService.getOrCreateVectorStore(base.getVectorCollectionName(), embeddingModelService.getOrCreateEmbeddingModel(embeddingModel));

                QuestionAnswerAdvisor advisor = new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder()
                        .query(userPrompt).topK(5).build());
                advisors.add(advisor);
            }
        }

        ChatClient chatClient = ChatClient.builder(chatModel)
                .defaultSystem(StrUtil.isNotBlank(systemPrompt) ? systemPrompt : DEFAULT_SYSTEM_PROMPT)
                .defaultAdvisors(advisors)
                .build();

        List<Message> messages = new ArrayList<>();
        messages.add(new UserMessage(userPrompt));
        Prompt prompt = new Prompt(messages, ChatOptions.builder()
                .model(model.getModelName())
                .build());
        Flux<ChatResponse> stream = chatClient.prompt(prompt).stream().chatResponse();

        StringBuilder answerContent = new StringBuilder();
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
                    answerContent.append(content);
                    if ("STOP".equalsIgnoreCase(finishReason)) {
                        sessionMessageService.add(AddSessionMessageParam.builder().parentId(questionId).sessionId(sessionId).role(MessageTypeEnum.ASSISTANT)
                                .content(answerContent.toString()).build());
                    }
                    ConversationStreamVO replyVO = ConversationStreamVO.builder()
                            .sessionId(sessionId)
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


    private String getOrCreateSessionId(String sessionId, String prompt, String modelId, SessionTypeEnum sessionSource, String agentId) {
        if (Objects.isNull(sessionId)) {
            return sessionService.add(AddSessionParam.builder().modelId(modelId).associationId(agentId).sessionType(sessionSource).name(prompt).build());
        }
        SessionVO session = sessionService.getById(sessionId);
        if (Objects.nonNull(session)) {
            return session.getId();
        }
        return sessionService.add(AddSessionParam.builder().modelId(modelId).associationId(agentId).sessionType(sessionSource).name(prompt).build());
    }


}
