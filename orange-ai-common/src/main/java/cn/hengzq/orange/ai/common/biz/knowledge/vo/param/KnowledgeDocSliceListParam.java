package cn.hengzq.orange.ai.common.biz.knowledge.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "切片管理 - 查询所有的数据")
public class KnowledgeDocSliceListParam implements Serializable {

    @Schema(description = "文档ID")
    private String docId;
}
