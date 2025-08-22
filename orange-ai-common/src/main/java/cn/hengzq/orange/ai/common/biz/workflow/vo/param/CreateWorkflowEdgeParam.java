package cn.hengzq.orange.ai.common.biz.workflow.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "工作流 - 边 - 创建参数")
public class CreateWorkflowEdgeParam implements Serializable {

    @Schema(description = "工作流ID")
    private String workflowId;

    @Schema(description = "源节点ID")
    private String sourceNodeId;

    @Schema(description = "目标节点ID")
    private String targetNodeId;
}