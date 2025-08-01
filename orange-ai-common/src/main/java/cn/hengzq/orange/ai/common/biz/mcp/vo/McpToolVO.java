package cn.hengzq.orange.ai.common.biz.mcp.vo;

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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "MCP 服务工具 VO")
public class McpToolVO implements Serializable {

    @Schema(description = "工具名称")
    private String name;

    @Schema(description = "工具描述")
    private String description;

    @Schema(description = "属性字段")
    private List<McpToolPropertyVO> properties;


}
