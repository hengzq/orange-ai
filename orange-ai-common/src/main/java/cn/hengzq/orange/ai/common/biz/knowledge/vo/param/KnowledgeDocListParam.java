package cn.hengzq.orange.ai.common.biz.knowledge.vo.param;

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
@Schema(description = "文档管理 - 查询所有的数据")
public class KnowledgeDocListParam implements Serializable {

    @Schema(description = "知识库ID")
    private String baseId;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "IDS")
    private List<String> ids;

}
