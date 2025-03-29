package cn.hengzq.orange.ai.common.biz.agent.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "智能体 - 对话交流参数 VO")
public class AgentConversationParam implements Serializable {

    @Schema(description = "智能体ID")
    private String agentId;

    @Schema(description = "回话ID")
    private String sessionId;

    @Schema(description = "生成图片的提示")
    private String prompt;
}
