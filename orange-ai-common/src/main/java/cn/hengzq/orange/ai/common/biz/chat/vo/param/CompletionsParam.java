package cn.hengzq.orange.ai.common.biz.chat.vo.param;

import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "对话参数 VO")
public class CompletionsParam {

    @Schema(description = "对话类型")
    private SessionTypeEnum sessionType;

    @Schema(description = "智能体ID 用于chatType为AGENT时必填")
    private String agentId;

    @Schema(description = "回话ID")
    private String sessionId;

    @Schema(description = "模型ID")
    private String modelId;

    @Schema(description = "用户提示词")
    private String prompt;
}
