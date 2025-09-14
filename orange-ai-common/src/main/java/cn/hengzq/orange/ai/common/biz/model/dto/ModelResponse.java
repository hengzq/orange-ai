package cn.hengzq.orange.ai.common.biz.model.dto;

import cn.hengzq.orange.ai.common.constant.ModelTypeEnum;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "模型管理")
public class ModelResponse extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "模型所属平台")
    private PlatformEnum platform;

    @Schema(description = "模型类别")
    private ModelTypeEnum modelType;

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "原始模型名称 ")
    private String modelName;

    @Schema(description = "模型启用状态 true:启用 false：不启用")
    private boolean enabled;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "基础URL eg:ollama路径为http://localhost:11434")
    private String baseUrl;

    @Schema(description = "API KEY")
    private String apiKey;

    @Schema(description = "备注")
    private String description;
}
