package cn.hengzq.orange.ai.common.biz.app.vo;

import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "应用 - 模型VO")
public class AppModelVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "应用ID")
    private String appId;

    @Schema(description = "版本ID")
    private String appVersionId;

    @Schema(description = "模型ID")
    private String modelId;

}
