package cn.hengzq.orange.ai.common.biz.app.vo;

import cn.hengzq.orange.ai.common.biz.app.constant.AppVersionStatusEnum;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelConfig;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "应用版本 - VO")
public class AppVersionVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "应用ID")
    private String appId;

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "系统提示词")
    private String systemPrompt;

    @Schema(description = "应用描述")
    private String description;

    @Schema(description = "模型ID")
    private String modelId;

    @Schema(description = "模型配置")
    private ModelConfig modelConfig;

    @Schema(description = "关联知识库ID")
    private List<String> baseIds;

    @Schema(description = "知识库配置")
    private AppBaseConfig baseConfig;

    @Schema(description = "MCP 服务IDS")
    private List<String> mcpIds;

    @Schema(description = "工作流IDS")
    private List<String> workflowIds;

    @Schema(description = "版本状态")
    private AppVersionStatusEnum versionStatus;

    @Schema(description = "发布人")
    private String publishBy;

    @Schema(description = "发布时间")
    private LocalDateTime publishAt;
}
