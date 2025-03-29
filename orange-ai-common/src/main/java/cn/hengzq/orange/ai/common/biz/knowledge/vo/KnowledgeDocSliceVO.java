package cn.hengzq.orange.ai.common.biz.knowledge.vo;

import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "知识库管理 - 知识文档 - 切片")
public class KnowledgeDocSliceVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "知识库ID")
     private String baseId;

    @Schema(description = "文档ID")
    private String docId;

    @Schema(description = "切片内容")
    private String content;
}
