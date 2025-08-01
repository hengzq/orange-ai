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
@Schema(description = "文档管理 - 文档 - 切片修改参数")
public class UpdateDocSliceParam implements Serializable {

    @NotNull(message = KnowledgeErrorCode.KNOWLEDGE_BASE_ID_CANNOT_NULL_KEY)
    @Schema(description = "知识库ID")
     private String baseId;

    @NotNull(message = KnowledgeErrorCode.DOC_ID_CANNOT_NULL_KEY)
    @Schema(description = "文档ID")
    private String docId;

    @Schema(description = "切片内容")
    private String text;


}
