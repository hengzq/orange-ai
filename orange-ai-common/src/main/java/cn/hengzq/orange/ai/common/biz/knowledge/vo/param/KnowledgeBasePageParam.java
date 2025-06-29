package cn.hengzq.orange.ai.common.biz.knowledge.vo.param;

import cn.hengzq.orange.common.dto.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "知识库管理 - 查询所有的数据")
public class KnowledgeBasePageParam extends PageParam {

    @Schema(description = "知识库名称,模糊匹配")
    private String name;

    @Schema(description = "嵌入式模型ID")
    private String embeddingModelId;

}
