package cn.hengzq.orange.ai.common.biz.workflow.vo;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowRunScopeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowRunStatusEnum;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "工作流 - 运行详情VO")
public class WorkflowRunDetailVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "工作流ID")
    private String workflowId;

    @Schema(description = "工作流版本ID")
    private String workflowVersionId;

    @Schema(description = "运行范围")
    private WorkflowRunScopeEnum runScope;

    @Schema(description = "运行状态")
    private WorkflowRunStatusEnum runStatus;

    @Schema(description = "运行节点")
    private List<WorkflowRunNodeVO> nodes;

}
