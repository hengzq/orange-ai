package cn.hengzq.orange.ai.common.biz.app.vo;

import cn.hengzq.orange.ai.common.biz.chat.constant.ConverstationEventEnum;
import cn.hengzq.orange.ai.common.biz.chat.vo.TokenUsageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@Schema(description = "对话回复 VO")
public class ConversationStreamVO implements Serializable {

    @Schema(description = "会话ID")
    private String sessionId;

    @Schema(description = "对话事件")
    private ConverstationEventEnum event;

    @Schema(description = "回复内容")
    private String content;

    @Schema(description = "Token使用量")
    private TokenUsageVO tokenUsage;

}
