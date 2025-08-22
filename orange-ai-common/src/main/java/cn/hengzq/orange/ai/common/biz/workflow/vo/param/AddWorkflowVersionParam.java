package cn.hengzq.orange.ai.common.biz.workflow.vo.param;

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
@Schema(description = "工作流版本 - 新增")
public class AddWorkflowVersionParam implements Serializable {


    @Schema(description = "工作流ID")
    private String workflowId;

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "系统提示词")
    private String systemPrompt;

    @Schema(description = "应用描述")
    private String description;


}
