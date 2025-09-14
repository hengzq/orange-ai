package cn.hengzq.orange.ai.common.biz.workflow.dto;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowVersionStatusEnum;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "工作流版本 - VO")
public class WorkflowVersionVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "工作流ID")
    private String workflowId;

    @Schema(description = "工作流名称")
    private String name;

    @Schema(description = "应用描述")
    private String description;

    @Schema(description = "版本状态")
    private WorkflowVersionStatusEnum versionStatus;

    @Schema(description = "发布人")
    private String publishBy;

    @Schema(description = "发布时间")
    private LocalDateTime publishAt;
}
