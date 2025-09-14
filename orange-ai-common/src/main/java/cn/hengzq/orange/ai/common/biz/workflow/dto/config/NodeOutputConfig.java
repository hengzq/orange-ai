package cn.hengzq.orange.ai.common.biz.workflow.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "节点输出数据")
public class NodeOutputConfig {

    @Schema(description = "输出参数")
    private List<Param> outParams;


}
