package cn.hengzq.orange.ai.common.biz.knowledge.event;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.DocRefreshType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文档事件相关参数")
public class DocRefreshParam {

    /**
     * 根据文档ID进行批量处理
     */
    private List<String> docIds;


    private DocRefreshType type;
}
