package cn.hengzq.orange.ai.common.biz.workflow.dto.request;

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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "工作流管理 - 节点 - 查询所有的数据")
public class WorkflowNodeListParam implements Serializable {

    @Schema(description = "工作流版本ID")
    private String workflowVersionId;

}
