package cn.hengzq.orange.ai.common.biz.workflow.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "工作流管理 - 更新参数")
public class UpdateWorkflowParam implements Serializable {

    @Schema(description = "工作流名称")
    private String name;

    @Schema(description = "工作流描述")
    private String description;


}
