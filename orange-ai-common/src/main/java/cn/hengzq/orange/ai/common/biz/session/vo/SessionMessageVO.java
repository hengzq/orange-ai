package cn.hengzq.orange.ai.common.biz.session.vo;

import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "会话消息 VO")
public class SessionMessageVO extends BaseTenantDTO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "会话ID")
    private Long sessionId;

    @Schema(description = "消息类型")
    private MessageTypeEnum role;

    @Schema(description = "回复内容")
    private String content;


}
