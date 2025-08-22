package cn.hengzq.orange.ai.common.biz.workflow.vo.param;

import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowEdgeVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowNodeVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hengzq
 */
@Data
@Schema(description = "工作流管理 - 流程图 - 更新参数")
public class UpdateWorkflowGraphParam implements Serializable {

    @Schema(description = "工作流节点列表")
    private List<WorkflowNodeVO> nodes;

    @Schema(description = "工作流边列表")
    private List<WorkflowEdgeVO> edges;

}
