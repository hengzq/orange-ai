package cn.hengzq.orange.ai.common.biz.workflow.dto.config;

import cn.hengzq.orange.ai.common.biz.workflow.constant.ParamTypeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.constant.ValueFromEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "参数")
public class Param {

    @Schema(description = "参数名称")
    private String name;

    @Schema(description = "参数类型")
    private ParamTypeEnum type;

    @Schema(description = "值的来源")
    private ValueFromEnum valueFrom;

    @Schema(description = "参数值")
    private String value;

    @Schema(description = "参数描述")
    private String description;
}
