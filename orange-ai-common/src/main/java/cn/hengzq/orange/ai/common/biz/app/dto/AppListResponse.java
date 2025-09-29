package cn.hengzq.orange.ai.common.biz.app.dto;

import cn.hengzq.orange.ai.common.biz.app.constant.AppStatusEnum;
import cn.hengzq.orange.ai.common.biz.app.constant.AppTypeEnum;
import cn.hengzq.orange.ai.common.biz.model.dto.ModelConfig;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "应用 - 列表响应参数")
public class AppListResponse extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "系统提示词")
    private String systemPrompt;

    @Schema(description = "应用类型")
    private AppTypeEnum appType;

    @Schema(description = "应用状态")
    private AppStatusEnum appStatus;

    @Schema(description = "草稿版本ID")
    private String draftVersionId;

    @Schema(description = "发布版本ID")
    private String publishedVersionId;

    @Schema(description = "应用描述")
    private String description;

}
