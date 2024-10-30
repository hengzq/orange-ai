package cn.hengzq.orange.ai.common.vo.chat;

import cn.hengzq.orange.ai.common.vo.TokenUsageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@Schema(description = "对话回复 VO")
public class ConversationReplyVO implements Serializable {

    @Schema(description = "回复内容")
    private String content;

    @Schema(description = "Token使用量")
    private TokenUsageVO tokenUsage;

}
