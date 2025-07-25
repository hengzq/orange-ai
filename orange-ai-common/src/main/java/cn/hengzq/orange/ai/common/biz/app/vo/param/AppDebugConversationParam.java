package cn.hengzq.orange.ai.common.biz.app.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "智能体 - 调试 - 对话交流参数 VO")
public class AppDebugConversationParam extends AddAppParam {

    @Schema(description = "回话ID")
    private String sessionId;

    @Schema(description = "生成图片的提示")
    private String prompt;
}
