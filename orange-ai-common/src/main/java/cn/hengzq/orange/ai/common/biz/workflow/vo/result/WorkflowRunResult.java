package cn.hengzq.orange.ai.common.biz.workflow.vo.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "工作流 - 执行结果")
public class WorkflowRunResult implements Serializable {

    @Schema(description = "运行ID")
    private String id;

    @Schema(description = "工作流ID")
    private String workflowId;


}
