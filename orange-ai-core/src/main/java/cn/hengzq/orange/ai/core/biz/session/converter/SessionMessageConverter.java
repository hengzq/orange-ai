package cn.hengzq.orange.ai.core.biz.session.converter;


import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.biz.session.vo.SessionMessageVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionMessageParam;
import cn.hengzq.orange.ai.core.biz.session.entity.SessionMessageEntity;
import cn.hengzq.orange.common.converter.Converter;
import cn.hutool.core.collection.CollUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天会话记录换装器
 *
 * @author hengzq
 */
@Mapper
public interface SessionMessageConverter extends Converter {

    SessionMessageConverter INSTANCE = Mappers.getMapper(SessionMessageConverter.class);

    List<SessionMessageVO> toListVO(List<SessionMessageEntity> entityList);

    SessionMessageEntity toEntity(AddSessionMessageParam param);

    default List<Message> toMessageList(List<SessionMessageVO> messages) {
        if (CollUtil.isEmpty(messages)) {
            return null;
        }
        List<Message> messageList = new ArrayList<>(messages.size());
        for (SessionMessageVO message : messages) {
            if (MessageTypeEnum.ASSISTANT.equals(message.getRole())) {
                messageList.add(new AssistantMessage(message.getContent()));
                continue;
            }
            if (MessageTypeEnum.USER.equals(message.getRole())) {
                messageList.add(new UserMessage(message.getContent()));
                continue;
            }
        }
        return messageList;
    }
}
