package cn.hengzq.orange.ai.common.biz.workflow.vo;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowNodeTypeEnum;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "工作流 - 边 -  VO")
public class WorkflowEdgeVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "工作流ID")
    private String workflowId;

    @Schema(description = "工作流版本ID")
    private String workflowVersionId;

    @Schema(description = "源节点ID")
    private String sourceNodeId;

    @Schema(description = "目标节点ID")
    private String targetNodeId;

}
