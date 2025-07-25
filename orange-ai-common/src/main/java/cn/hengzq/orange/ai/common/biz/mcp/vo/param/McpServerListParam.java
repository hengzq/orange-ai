package cn.hengzq.orange.ai.common.biz.mcp.vo.param;

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
@Schema(description = "MCP 服务管理 VO - 查询所有的数据")
public class McpServerListParam implements Serializable {

    @Schema(description = "服务名称，模糊查询")
    private String name;

    @Schema(description = "IDS")
    private List<String> ids;

    @Schema(description = "启用状态 true:启用 false：不启用")
    private Boolean enabled;

}
