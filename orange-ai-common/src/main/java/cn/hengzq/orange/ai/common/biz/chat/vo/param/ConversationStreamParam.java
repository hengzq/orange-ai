package cn.hengzq.orange.ai.common.biz.chat.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "对话交流参数 VO")
public class ConversationStreamParam {

    @Schema(description = "回话ID")
    private String sessionId;

    @Schema(description = "模型ID")
    private String modelId;

    @Schema(description = "提示词")
    private String prompt;
}
