package cn.hengzq.orange.ai.common.biz.chat.dto;

import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import lombok.Builder;
import lombok.Data;
import org.springframework.ai.chat.messages.Message;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class ChatModelConversationParam {

    /**
     * 提示词
     */
    private String prompt;

    /**
     * 上下文对话信息
     */
    List<Message> messages = Collections.emptyList();

    /**
     * 模型
     */
    private ModelVO model;
}
