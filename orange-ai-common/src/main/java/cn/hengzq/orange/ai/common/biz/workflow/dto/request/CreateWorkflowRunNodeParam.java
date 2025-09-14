package cn.hengzq.orange.ai.common.biz.workflow.dto.request;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowRunStatusEnum;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "工作流管理 - 运行节点 - 创建参数")
public class CreateWorkflowRunNodeParam implements Serializable {

    @Schema(description = "运行ID")
    private String runId;

    @Schema(description = "节点ID")
    private String nodeId;

    @Schema(description = "运行状态")
    private WorkflowRunStatusEnum runStatus;

    @Schema(description = "输入数据")
    private JSONObject inputData;

}
