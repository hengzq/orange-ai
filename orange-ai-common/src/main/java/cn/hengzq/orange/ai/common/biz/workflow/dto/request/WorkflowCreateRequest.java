package cn.hengzq.orange.ai.common.biz.workflow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "工作流 - 新增参数")
public class WorkflowCreateRequest implements Serializable {

    @Schema(description = "工作流名称")
    private String name;

    @Schema(description = "工作流描述")
    private String description;
}
