package cn.hengzq.orange.ai.common.biz.agent.vo;

import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeBaseVO;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelConfig;
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
@Schema(description = "智能体管理")
public class AgentVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "模型ID")
    private String modelId;

    @Schema(description = "模型配置")
    private ModelConfig modelConfig;

    @Schema(description = "关联知识库ID")
    private List<String> baseIds;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "系统提示词")
    private String systemPrompt;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "关联知识库列表")
    private List<KnowledgeBaseVO> baseList;
}
