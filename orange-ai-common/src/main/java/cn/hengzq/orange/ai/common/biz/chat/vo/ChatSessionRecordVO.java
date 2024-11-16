package cn.hengzq.orange.ai.common.biz.chat.vo;

import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.biz.chat.constant.RatingEnum;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "会话记录 VO")
public class ChatSessionRecordVO extends BaseTenantDTO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "会话ID")
    private Long sessionId;

    @Schema(description = "消息类型")
    private MessageTypeEnum messageType;

    @Schema(description = "回复内容")
    private String content;

    @Schema(description = "回话内容评价")
    private RatingEnum rating;

}
