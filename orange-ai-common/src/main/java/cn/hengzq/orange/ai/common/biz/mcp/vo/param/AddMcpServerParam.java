package cn.hengzq.orange.ai.common.biz.mcp.vo.param;

import cn.hengzq.orange.ai.common.biz.mcp.constant.TransportProtocolEnum;
import cn.hengzq.orange.ai.common.constant.ModelTypeEnum;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "MCP 服务管理 VO - 新增参数")
public class AddMcpServerParam implements Serializable {

    @Schema(description = "服务名称")
    private String name;

    @Schema(description = "传输协议")
    private TransportProtocolEnum transportProtocol;

    @Schema(description = "SSE 服务连接地址")
    private String connectionUrl;

    @Schema(description = "备注")
    private String description;
}
