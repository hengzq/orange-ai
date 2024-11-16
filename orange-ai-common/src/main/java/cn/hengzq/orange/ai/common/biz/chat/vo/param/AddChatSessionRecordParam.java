package cn.hengzq.orange.ai.common.biz.chat.vo.param;

import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会话记录 - 新增参数")
public class AddChatSessionRecordParam implements Serializable {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "会话ID")
    private Long sessionId;

    @Schema(description = "消息类型")
    private MessageTypeEnum messageType;

    @Schema(description = "回复内容")
    private String content;

}
