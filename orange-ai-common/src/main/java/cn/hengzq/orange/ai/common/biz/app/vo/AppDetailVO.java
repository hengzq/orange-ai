package cn.hengzq.orange.ai.common.biz.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "应用 - 详细VO")
public class AppDetailVO implements Serializable {

    @Schema(description = "应用")
    private AppVO app;

    @Schema(description = "版本")
    private AppVersionVO version;

    @Schema(description = "模型")
    private AppModelVO model;

}
