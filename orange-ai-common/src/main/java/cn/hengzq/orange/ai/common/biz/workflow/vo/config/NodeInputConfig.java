package cn.hengzq.orange.ai.common.biz.workflow.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "节点输入数据")
public class NodeInputConfig {

    @Schema(description = "输入参数")
    private List<Param> inputParams;

    @Schema(description = "大模型节点参数")
    private LlmNodeParameter llmParam;
}
