package cn.hengzq.orange.ai.common.biz.workflow.dto;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowNodeTypeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.NodeInputConfig;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.NodeOutputConfig;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.Position;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "工作流 - 节点 -  VO")
public class WorkflowNodeVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "工作流ID")
    private String workflowId;

    @Schema(description = "工作流版本ID")
    private String workflowVersionId;

    @Schema(description = "节点编码")
    private String nodeCode;

    @Schema(description = "节点名称")
    private String name;

    @Schema(description = "节点类型")
    private WorkflowNodeTypeEnum nodeType;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "节点位置")
    private Position position;

    @Schema(description = "输入相关参数")
    private NodeInputConfig inputConfig;

    @Schema(description = "输出相关参数")
    private NodeOutputConfig outputConfig;
}

