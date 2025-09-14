package cn.hengzq.orange.ai.common.biz.knowledge.vo;

import cn.hengzq.orange.ai.common.biz.model.dto.ModelResponse;
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
@Schema(description = "知识库管理")
public class BaseVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "知识库名称")
    private String name;

    @Schema(description = "所属平台")
    private PlatformEnum platform;

    @Schema(description = "嵌入式模型ID")
    private String embeddingModelId;

    @Schema(description = "嵌入式模型")
    private ModelResponse embeddingModel;

    @Schema(description = "模型启用状态 true:启用 false：不启用")
    private boolean enabled;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "知识库对应向量集合名称")
    private String vectorCollectionName;
}
