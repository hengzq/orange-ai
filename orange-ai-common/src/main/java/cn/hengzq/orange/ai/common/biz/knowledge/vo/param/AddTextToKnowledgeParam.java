package cn.hengzq.orange.ai.common.biz.knowledge.vo.param;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.KnowledgeErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "知识库管理 - 文本内容保存为知识参数")
public class AddTextToKnowledgeParam implements Serializable {

    @NotNull(message = KnowledgeErrorCode.KNOWLEDGE_BASE_ID_CANNOT_NULL_KEY)
    @Schema(description = "知识库ID")
    private String baseId;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "知识内容")
    private String content;

}
