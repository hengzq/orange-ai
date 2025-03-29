package cn.hengzq.orange.ai.common.biz.knowledge.vo.param;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "知识库管理 - 新增参数")
public class AddKnowledgeBaseParam implements Serializable {

    @Schema(description = "知识库名称")
    private String name;

    @Schema(description = "嵌入式模型ID")
    private String embeddingModelId;

    @Schema(description = "模型启用状态 true:启用 false：不启用")
    private boolean enabled;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注")
    private String description;
}
