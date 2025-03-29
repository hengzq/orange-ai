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
@Schema(description = "切片管理 - 分页查询")
public class KnowledgeDocSlicePageParam extends PageParam {

    @Schema(description = "文档ID")
    private String docId;


}
