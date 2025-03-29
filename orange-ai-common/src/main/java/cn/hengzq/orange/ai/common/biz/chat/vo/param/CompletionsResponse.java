package cn.hengzq.orange.ai.common.biz.chat.vo.param;

import cn.hengzq.orange.ai.common.biz.chat.constant.ConverstationEventEnum;
import cn.hengzq.orange.ai.common.biz.chat.vo.TokenUsageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "对话回复响应")
public class CompletionsResponse {

    @Schema(description = "会话ID")
    private String sessionId;

    @Schema(description = "对话事件")
    private ConverstationEventEnum event;

    @Schema(description = "回复内容")
    private String content;
//
//    @Schema(description = "Token使用量")
//    private TokenUsageVO tokenUsage;

}
