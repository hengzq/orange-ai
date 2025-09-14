package cn.hengzq.orange.ai.common.biz.workflow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author hengzq
 */
@Data
@Schema(description = "工作流管理 - 运行参数")
public class WorkflowRunParam implements Serializable {

    @Schema(description = "工作流版本ID")
    private String workflowVersionId;

    @Schema(description = "输入参数")
    private Map<String, Object> input;


}
