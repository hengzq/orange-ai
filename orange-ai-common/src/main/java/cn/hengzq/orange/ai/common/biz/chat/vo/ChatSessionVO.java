package cn.hengzq.orange.ai.common.biz.chat.vo;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "会话管理 VO")
public class ChatSessionVO extends BaseTenantDTO {

    private Long id;

    @Schema(description = "用户ID")
    private Long userId;


    @Schema(description = "会话名称")
    private String name;


    @Schema(description = "所属平台")
    private PlatformEnum platform;


    @Schema(description = "模型编码")
    private String modelCode;

}
