package cn.hengzq.orange.ai.common.biz.agent.vo.param;

import cn.hengzq.orange.ai.common.biz.model.vo.ModelConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hengzq
 */
@Data
@Schema(description = "智能体管理 - 更新参数")
public class UpdateAgentParam implements Serializable {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "模型ID")
    private Long modelId;

    @Schema(description = "模型配置")
    private ModelConfig modelConfig;

    @Schema(description = "关联知识库")
    private List<Long> baseIds;

    @Schema(description = "系统提示词")
    private String systemPrompt;

    @Schema(description = "模型描述")
    private String description;
}
