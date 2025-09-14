package cn.hengzq.orange.ai.common.biz.workflow.dto.request;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowRunScopeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.Param;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author hengzq
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "工作流管理 - 运行 - 创建参数")
public class CreateWorkflowRunParam implements Serializable {

    @Schema(description = "运行范围：WORKFLOW（整个工作流），NODE（单个节点独立执行）")
    private WorkflowRunScopeEnum runScope;

    @Schema(description = "工作流ID")
    private String workflowId;

    @Schema(description = "工作流版本ID")
    private String workflowVersionId;

    @Schema(description = "运行参数")
    private List<Param> params;


}
