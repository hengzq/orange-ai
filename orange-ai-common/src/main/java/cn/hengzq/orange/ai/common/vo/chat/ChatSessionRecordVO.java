package cn.hengzq.orange.ai.common.vo.chat;

import cn.hengzq.orange.common.dto.BaseTenantDTO;
import cn.hengzq.orange.ai.common.constant.MessageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Schema(description = "会话记录 VO")
public class ChatSessionRecordVO extends BaseTenantDTO {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "会话ID")
    private Long sessionId;

    @Schema(description = "消息类型")
    private MessageTypeEnum messageType;

    @Schema(description = "回复内容")
    private String content;

}
