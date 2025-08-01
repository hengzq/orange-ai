package cn.hengzq.orange.ai.common.biz.mcp.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "MCP 服务工具 - 属性")
public class McpToolPropertyVO implements Serializable {

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "属性描述")
    private String description;

}
