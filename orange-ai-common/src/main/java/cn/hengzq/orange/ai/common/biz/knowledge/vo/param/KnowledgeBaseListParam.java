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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "知识库管理 - 查询所有的数据")
public class KnowledgeBaseListParam implements Serializable {

    @Schema(description = "知识库名称")
    private String name;

    @Schema(description = "模型名称,模糊匹配")
    private String nameLike;

    @Schema(description = "模型启用状态 true:启用 false：不启用")
    private Boolean enabled;

    @Schema(description = "知识库IDS列表")
    private List<String> ids;
}
