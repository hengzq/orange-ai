package cn.hengzq.orange.ai.common.biz.mcp.vo.param;

import cn.hengzq.orange.common.dto.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "MCP 服务管理 VO -查询所有的数据")
public class McpServerPageParam extends PageParam {

    @Schema(description = "服务名称，模糊查询")
    private String name;

    @Schema(description = "启用状态 true:启用 false：不启用")
    private Boolean enabled;

}
