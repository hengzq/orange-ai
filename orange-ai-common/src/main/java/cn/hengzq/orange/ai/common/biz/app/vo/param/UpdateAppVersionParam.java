package cn.hengzq.orange.ai.common.biz.app.vo.param;

import cn.hengzq.orange.ai.common.biz.app.vo.AppBaseConfig;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author hengzq
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "应用版本 - 更新参数")
public class UpdateAppVersionParam implements Serializable {

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


}
