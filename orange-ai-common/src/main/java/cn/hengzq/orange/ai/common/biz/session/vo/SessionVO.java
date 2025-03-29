package cn.hengzq.orange.ai.common.biz.session.vo;

import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "会话管理 VO")
public class SessionVO extends BaseTenantDTO {

    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "会话名称")
    private String name;

    @Schema(description = "模型ID")
    private String modelId;

    @Schema(description = "关联外键ID")
    private String associationId;

    @Schema(description = "会话类型")
    private SessionTypeEnum sessionType;

}
