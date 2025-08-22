package cn.hengzq.orange.ai.common.biz.workflow.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "大模型节点配置")
public class LlmNodeParameter {

    @Schema(description = "模型ID")
    private String modelId;

    @Schema(description = "系统提示词")
    private String systemPrompt;

    @Schema(description = "用户提示词")
    private String prompt;


}
