package cn.hengzq.orange.ai.common.biz.prompt.vo.param;

import cn.hengzq.orange.ai.common.biz.prompt.constant.PromptTemplateEnum;
import cn.hengzq.orange.ai.common.constant.ModelTypeEnum;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author hengzq
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Prompt 模板管理 - 查询所有的数据")
public class PromptTemplateListParam implements Serializable {

    @Schema(description = "模板名称 模糊查询")
    private String name;

    @Schema(description = "模板类型")
    private PromptTemplateEnum templateType;

}
