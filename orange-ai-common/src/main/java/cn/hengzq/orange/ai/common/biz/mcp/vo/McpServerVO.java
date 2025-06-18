package cn.hengzq.orange.ai.common.biz.mcp.vo;

import cn.hengzq.orange.ai.common.biz.mcp.constant.TransportProtocolEnum;
import cn.hengzq.orange.common.dto.BaseTenantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "MCP 服务管理 VO")
public class McpServerVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "服务名称")
    private String name;

    @Schema(description = "传输协议")
    private TransportProtocolEnum transportProtocol;

    @Schema(description = "SSE 服务连接地址")
    private String connectionUrl;

    @Schema(description = "备注")
    private String description;
}
