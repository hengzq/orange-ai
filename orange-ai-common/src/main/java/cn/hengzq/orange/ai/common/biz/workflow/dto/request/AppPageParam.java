package cn.hengzq.orange.ai.common.biz.workflow.dto.request;

import cn.hengzq.orange.common.dto.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "应用管理 - 查询所有的数据")
public class AppPageParam extends PageParam {

    @Schema(description = "模型名称 模糊匹配")
    private String name;

}
