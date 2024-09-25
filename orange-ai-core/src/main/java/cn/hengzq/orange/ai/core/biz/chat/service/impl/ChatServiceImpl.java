package cn.hengzq.orange.ai.core.biz.chat.service.impl;

import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.ai.core.biz.chat.converter.ChatSessionConverter;
import cn.hengzq.orange.ai.core.biz.chat.entity.ChatSessionEntity;
import cn.hengzq.orange.ai.core.biz.chat.mapper.ChatSessionMapper;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatService;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatSessionRecordService;
import cn.hengzq.orange.ai.common.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.AddChatSessionRecordParam;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hengzq.orange.context.GlobalContextHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatModelServiceFactory chatModelServiceFactory;

    private final ChatSessionMapper chatSessionMapper;

    private final ChatSessionRecordService chatSessionRecordService;

    @Override
    public ConversationReplyVO conversation(ConversationParam param) {
        return null;
    }

    @Override
    public Flux<Result<ConversationReplyVO>> conversationStream(ConversationParam param) {
        Long sessionId = getSessionId(param);

        chatSessionRecordService.add(AddChatSessionRecordParam.builder()
                .userId(GlobalContextHelper.getUserId())
                .sessionId(sessionId)
                .messageType(MessageTypeEnum.USER)
                .content(param.getPrompt())
                .build());
        ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(param.getPlatform());
        AddChatSessionRecordParam assistant = AddChatSessionRecordParam.builder()
                .userId(GlobalContextHelper.getUserId())
                .sessionId(sessionId)
                .messageType(MessageTypeEnum.ASSISTANT)
                .content(param.getPrompt())
                .build();
        StringBuilder content = new StringBuilder();
        return chatModelService.conversationStream(param)
                .filter(Objects::nonNull)
                .map(result -> {
                    content.append(result.getData().getContent());
                    return result;
                })
                .doOnComplete(() -> {
                    assistant.setContent(content.toString());
                    chatSessionRecordService.add(assistant);
                }).onErrorResume(error -> {
                    log.error("An error occurred: {}", error.getMessage(), error);
                    return Mono.just(ResultWrapper.fail());
                });
    }

    private Long getSessionId(ConversationParam param) {
        Long sessionId = param.getSessionId();
        if (Objects.isNull(sessionId)) {
            ChatSessionEntity chatSessionEntity = ChatSessionConverter.INSTANCE.toEntity(param);
            chatSessionEntity.setUserId(-100L);
            chatSessionEntity.setCreatedBy(-100L);
            sessionId = chatSessionMapper.insertOne(chatSessionEntity);
        }
        return sessionId;
    }
}
