package cn.hengzq.orange.ai.core.core.chat.service.impl;

import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.ai.core.core.chat.converter.ChatSessionConverter;
import cn.hengzq.orange.ai.core.core.chat.entity.ChatSessionEntity;
import cn.hengzq.orange.ai.core.core.chat.mapper.ChatSessionMapper;
import cn.hengzq.orange.ai.core.core.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.core.chat.service.ChatService;
import cn.hengzq.orange.ai.core.core.chat.service.ChatSessionRecordService;
import cn.hengzq.orange.ai.common.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.AddChatSessionRecordParam;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import cn.hengzq.orange.context.GlobalContextHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
        return chatModelService.conversationStream(param)
                .map(result -> {
                    assistant.setContent(result.getData().getContent());
                    return result;
                })
                .doOnComplete(() -> {
                    chatSessionRecordService.add(assistant);
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
