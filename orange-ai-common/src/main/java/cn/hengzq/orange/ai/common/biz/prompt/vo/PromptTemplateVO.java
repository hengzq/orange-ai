package cn.hengzq.orange.ai.common.biz.prompt.vo;

import cn.hengzq.orange.ai.common.biz.prompt.constant.PromptTemplateEnum;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "提示词 模板")
public class PromptTemplateVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "模板名称")
    private String name;

    @Schema(description = "模板类型")
    private PromptTemplateEnum templateType;

    @Schema(description = "模板内容")
    private String templateContent;
}
