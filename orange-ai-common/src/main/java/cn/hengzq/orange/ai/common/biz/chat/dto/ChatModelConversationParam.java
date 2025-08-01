package cn.hengzq.orange.ai.common.biz.chat.dto;

import cn.hengzq.orange.ai.common.biz.knowledge.vo.BaseVO;
import cn.hengzq.orange.ai.common.biz.mcp.vo.McpServerVO;
import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class ChatModelConversationParam {

    /**
     * 回话ID
     */
    private String sessionId;

    /**
     * 回话关联外键ID，eg：应用ID
     */
    private String sessionAssociationId;

    /**
     * 会话类型
     */
    private SessionTypeEnum sessionType;

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
    List<Message> messages = Collections.emptyList();

    /**
     * 模型参数
     */
    private ChatModelOptions modelOptions;


    /**
     * 对话使用到的知识库
     */
    private List<BaseVO> baseList;

    /**
     * 对话使用的向量数据库
     */
    private List<VectorStore> vectorStores;

    /**
     * MCP 服务
     */
    private List<McpServerVO> mcpServerList;

}
