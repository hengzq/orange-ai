package cn.hengzq.orange.ai.common.biz.chat.vo.param;

import cn.hengzq.orange.ai.common.biz.model.vo.ModelConfig;
import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatConversationParam {

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
     * 模型ID
     */
    private String modelId;

    /**
     * 模型配置
     */
    private ModelConfig modelConfig;

    /**
     * 对话使用到的知识库
     */
    private List<String> baseIds;

    /**
     * 关联 MCP 服务
     */
    private List<String> mcpIds;

}
