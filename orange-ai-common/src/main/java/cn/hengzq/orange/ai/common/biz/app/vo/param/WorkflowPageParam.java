package cn.hengzq.orange.ai.common.biz.app.vo.param;

import cn.hengzq.orange.common.dto.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "工作流管理 - 分页查询的数据")
public class WorkflowPageParam extends PageParam {

    @Schema(description = "流水线名称 模糊匹配")
    private String name;

}
