package cn.hengzq.orange.ai.common.biz.workflow.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "位置")
public class Position {

    @Schema(description = "X坐标")
    private Integer x;

    @Schema(description = "Y坐标")
    private Integer y;
}
