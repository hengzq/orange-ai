package cn.hengzq.orange.ai.common.biz.chat.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

/**
 * 聊天参数
 */
@Data
@Builder
public class ChatParam {

    /**
     * 提示词
     */
    private String prompt;

    /**
     * 系统提示词
     */
    private String systemPrompt;

    /**
     * 上下文对话信息
     */
    List<Message> messages;

    /**
     * 模型参数
     */
    private ChatModelOptions modelOptions;

    /**
     * 对话使用的向量数据库
     */
    private List<VectorStore> vectorStores;

    /**
     * 工具调用
     */
    private List<ToolCallback> callbacks;


}
