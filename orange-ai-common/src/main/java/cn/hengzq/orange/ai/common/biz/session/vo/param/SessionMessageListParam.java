package cn.hengzq.orange.ai.common.biz.session.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "会话记录管理 - 列表查询参数")
public class SessionMessageListParam implements Serializable {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "会话ID")
    private Long sessionId;

}
