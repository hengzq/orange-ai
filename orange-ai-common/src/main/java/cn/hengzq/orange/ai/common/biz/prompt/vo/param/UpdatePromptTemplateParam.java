package cn.hengzq.orange.ai.common.biz.prompt.vo.param;

import cn.hengzq.orange.ai.common.biz.prompt.constant.PromptTemplateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "Prompt 模板管理 - 更新参数")
public class UpdatePromptTemplateParam implements Serializable {

    @Schema(description = "模板名称")
    private String name;

    @Schema(description = "模板类型")
    private PromptTemplateEnum templateType;

    @Schema(description = "模板内容")
    private String templateContent;
}
