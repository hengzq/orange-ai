package cn.hengzq.orange.ai.common.vo.chat.param;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "对话交流参数 VO")
public class ConversationParam {

    @Schema(description = "回话ID")
    private Long sessionId;

    @Schema(description = "所属平台")
    private PlatformEnum platform = PlatformEnum.ALI_BAI_LIAN;

    @Schema(description = "模型编码")
    private String modelCode = "wanx-style-cosplay-v1";

    @Schema(description = "生成图片的提示")
    private String prompt;
}
