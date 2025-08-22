package cn.hengzq.orange.ai.common.biz.workflow.vo;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowStatusEnum;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "工作流 - VO")
public class WorkflowVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "工作流状态")
    private WorkflowStatusEnum workflowStatus;

    @Schema(description = "草稿版本ID")
    private String draftVersionId;

    @Schema(description = "发布版本ID")
    private String publishedVersionId;

    @Schema(description = "最新版")
    private WorkflowVersionVO latestVersion;

}
