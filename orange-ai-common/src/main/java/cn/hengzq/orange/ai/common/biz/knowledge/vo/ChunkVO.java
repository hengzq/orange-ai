package cn.hengzq.orange.ai.common.biz.knowledge.vo;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.SliceEmbStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "知识库管理 - 知识文档 - 切片")
public class ChunkVO implements Serializable {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "知识库ID")
    private String baseId;

    @Schema(description = "文档ID")
    private String docId;

    @Schema(description = "向量状态")
    private SliceEmbStatus embStatus;

    @Schema(description = "段落内容")
    private String text;
}
