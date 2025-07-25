package cn.hengzq.orange.ai.common.biz.mcp.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "MCP 服务管理 VO - 更新参数")
public class UpdateMcpServerParam implements Serializable {

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "SSE 服务连接地址")
    private String connectionUrl;

    @Schema(description = "SSE 协议端点")
    private String sseEndpoint;

    @Schema(description = "启用状态 true:启用 false：不启用")
    private boolean enabled;

    @Schema(description = "模型描述")
    private String description;
}
