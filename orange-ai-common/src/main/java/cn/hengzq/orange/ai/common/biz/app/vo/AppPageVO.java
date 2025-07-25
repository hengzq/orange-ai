package cn.hengzq.orange.ai.common.biz.app.vo;

import cn.hengzq.orange.ai.common.biz.app.constant.AppTypeEnum;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "应用分页 - VO")
public class AppPageVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "应用类型")
    private AppTypeEnum appType;

    @Schema(description = "备注")
    private String description;

}
