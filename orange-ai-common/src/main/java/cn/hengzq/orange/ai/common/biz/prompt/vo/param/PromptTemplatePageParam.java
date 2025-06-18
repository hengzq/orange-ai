package cn.hengzq.orange.ai.common.biz.prompt.vo.param;

import cn.hengzq.orange.ai.common.biz.prompt.constant.PromptTemplateEnum;
import cn.hengzq.orange.common.dto.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Prompt 模板管理 - 查询所有的数据")
public class PromptTemplatePageParam extends PageParam {

    @Schema(description = "模板名称 模糊查询")
    private String name;

    @Schema(description = "模板类型")
    private PromptTemplateEnum templateType;

}
