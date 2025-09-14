package cn.hengzq.orange.ai.common.biz.workflow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "工作流 - 版本 - 查询所有的数据")
public class WorkflowVersionListParam implements Serializable {

    @Schema(description = "工作流ID")
    private String workflowId;

    @Schema(description = "工作流名称")
    private String name;

}
