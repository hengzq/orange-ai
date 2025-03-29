package cn.hengzq.orange.ai.common.biz.session.vo.param;

import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.common.constant.GlobalConstant;
import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会话记录 - 新增参数")
public class AddSessionMessageParam implements Serializable {

    @Schema(description = "父级ID")
    private String parentId = GlobalConstant.DEFAULT_PARENT_ID + "";

    @Schema(description = "会话ID")
    private String sessionId;

    @Schema(description = "消息类型")
    private MessageTypeEnum role;

    @Schema(description = "回复内容")
    private String content;

}
