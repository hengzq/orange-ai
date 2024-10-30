package cn.hengzq.orange.ai.common.converter;

import cn.hengzq.orange.ai.common.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.vo.chat.ChatSessionRecordVO;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MessageConverter {

    public static List<Message> toMessageList(List<ChatSessionRecordVO> contextMessageList) {
        if (CollUtil.isEmpty(contextMessageList)) {
            return List.of();
        }
        List<Message> messageList = new ArrayList<>(contextMessageList.size());
        for (ChatSessionRecordVO item : contextMessageList) {
            if (MessageTypeEnum.USER.equals(item.getMessageType())) {
                messageList.add(new UserMessage(item.getContent()));
            } else if (MessageTypeEnum.ASSISTANT.equals(item.getMessageType())) {
                messageList.add(new AssistantMessage(item.getContent()));
            } else {
                log.error("messageType is error. {}", item.getMessageType());
            }
        }
        return messageList;
    }

}
