package cn.hengzq.orange.ai.common.biz.chat.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "智能体对话交流参数 VO")
public class AgentConversationStreamParam {

    @Schema(description = "回话ID")
    private String sessionId;

    @Schema(description = "智能体ID")
    private String agentId;

    @Schema(description = "提示词")
    private String prompt;
}
