package cn.hengzq.orange.ai.common.biz.workflow.vo.param;

import cn.hengzq.orange.ai.common.biz.app.constant.AppTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "工作流 - 新增参数")
public class AddWorkflowParam implements Serializable {

    @Schema(description = "工作流名称")
    private String name;

    @Schema(description = "工作流描述")
    private String description;
}
