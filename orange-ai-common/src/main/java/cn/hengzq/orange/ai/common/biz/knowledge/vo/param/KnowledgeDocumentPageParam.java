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
@Schema(description = "知识库管理 - 知识文档 - 查询所有的数据")
public class KnowledgeDocumentPageParam extends PageParam {

    @Schema(description = "知识库ID")
     private String baseId;

    @Schema(description = "文件名称")
    private String fileName;

}
