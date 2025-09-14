package cn.hengzq.orange.ai.common.biz.workflow.dto.request;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowNodeTypeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Builder
@Schema(description = "工作流 - 节点 - 创建参数")
public class CreateWorkflowNodeParam implements Serializable {

    @Schema(description = "工作流ID")
    private String workflowId;

    @Schema(description = "节点类型")
    private WorkflowNodeTypeEnum nodeType;

    @Schema(description = "节点位置")
    private Position position;
}
