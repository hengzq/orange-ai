package cn.hengzq.orange.ai.common.biz.knowledge.vo;

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
@Schema(description = "知识库管理 - 知识文档 - 文档切片")
public class DocSplitVO implements Serializable {

    @Schema(description = "文件信息")
    private FileInfo fileInfo;

    @Schema(description = "切片信息")
    private List<ChunkVO> chunks;

}
