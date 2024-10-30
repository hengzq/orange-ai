package cn.hengzq.orange.ai.core.biz.chat.service.impl;

import cn.hengzq.orange.ai.common.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import cn.hengzq.orange.ai.common.vo.TokenUsageVO;
import cn.hengzq.orange.ai.common.vo.chat.ChatSessionRecordVO;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import cn.hengzq.orange.ai.core.biz.chat.converter.ChatSessionConverter;
import cn.hengzq.orange.ai.core.biz.chat.converter.ChatSessionRecordConverter;
import cn.hengzq.orange.ai.core.biz.chat.entity.ChatSessionEntity;
import cn.hengzq.orange.ai.core.biz.chat.entity.ChatSessionRecordEntity;
import cn.hengzq.orange.ai.core.biz.chat.mapper.ChatSessionMapper;
import cn.hengzq.orange.ai.core.biz.chat.mapper.ChatSessionRecordMapper;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatService;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hengzq.orange.context.GlobalContextHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatModelServiceFactory chatModelServiceFactory;

    private final ChatSessionMapper chatSessionMapper;

    private final ChatSessionRecordMapper chatSessionRecordMapper;

    @Override
    public ConversationReplyVO conversation(ConversationParam param) {
        return null;
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param) {
        Long sessionId = getSessionId(param);

        // 获取对话上下文记录
        List<ChatSessionRecordEntity> entityList = chatSessionRecordMapper.selectListBySessionId(sessionId);
        List<ChatSessionRecordVO> contextMessageList = ChatSessionRecordConverter.INSTANCE.toListVO(entityList);

        // 保存用户prompt
        ChatSessionRecordEntity userRecord = generateRecord(sessionId, param, MessageTypeEnum.USER);
        ChatSessionRecordEntity assistantRecord = generateRecord(sessionId, param, MessageTypeEnum.ASSISTANT);

        ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(param.getPlatform());
        StringBuilder content = new StringBuilder();
        return chatModelService.conversationStream(param, contextMessageList)
                .filter(Objects::nonNull)
                .map(result -> {
                    content.append(result.getData().getContent());
                    TokenUsageVO tokenUsage = result.getData().getTokenUsage();
                    if (Objects.nonNull(tokenUsage)) {
                        userRecord.setTokenQuantity(result.getData().getTokenUsage().getPromptTokens());
                        assistantRecord.setTokenQuantity(result.getData().getTokenUsage().getGenerationTokens());
                    }
                    return result;
                })
                .doOnComplete(() -> {
                    assistantRecord.setContent(content.toString());
                    chatSessionRecordMapper.insert(userRecord);
                    chatSessionRecordMapper.insert(assistantRecord);
                }).onErrorResume(error -> {
                    log.error("An error occurred: {}", error.getMessage(), error);
                    return Mono.just(ResultWrapper.fail());
                });
    }

    private ChatSessionRecordEntity generateRecord(Long sessionId, ConversationParam param, MessageTypeEnum messageTypeEnum) {
        ChatSessionRecordEntity entity = new ChatSessionRecordEntity();
        entity.setUserId(GlobalContextHelper.getUserId());
        entity.setSessionId(sessionId);
        entity.setMessageType(messageTypeEnum);
        if (MessageTypeEnum.USER.equals(messageTypeEnum)) {
            entity.setContent(param.getPrompt());
            entity.setCreatedAt(LocalDateTime.now());
        }
        entity.setCreatedBy(GlobalContextHelper.getUserId());
        entity.setTenantId(GlobalContextHelper.getTenantId());
        return entity;
    }

    private Long getSessionId(ConversationParam param) {
        Long sessionId = param.getSessionId();
        if (Objects.isNull(sessionId)) {
            ChatSessionEntity chatSessionEntity = ChatSessionConverter.INSTANCE.toEntity(param);
            chatSessionEntity.setUserId(GlobalContextHelper.getUserId());
            sessionId = chatSessionMapper.insertOne(chatSessionEntity);
        }
        return sessionId;
    }
}
