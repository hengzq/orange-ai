package cn.hengzq.orange.ai.core.biz.mcp.converter;


import cn.hengzq.orange.ai.common.biz.mcp.vo.McpServerVO;
import cn.hengzq.orange.ai.common.biz.mcp.vo.McpToolPropertyVO;
import cn.hengzq.orange.ai.common.biz.mcp.vo.McpToolVO;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.AddMcpServerParam;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.UpdateMcpServerParam;
import cn.hengzq.orange.ai.core.biz.mcp.entity.McpServerEntity;
import cn.hengzq.orange.common.converter.Converter;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import io.modelcontextprotocol.spec.McpSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.*;

/**
 * @author hengzq
 */
@Mapper
public interface McpServerConverter extends Converter {

    McpServerConverter INSTANCE = Mappers.getMapper(McpServerConverter.class);

    McpServerEntity toEntity(McpServerVO McpServerVO);

    McpServerEntity toEntity(AddMcpServerParam request);

    McpServerVO toVO(McpServerEntity entity);

    List<McpServerVO> toListVO(List<McpServerEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "param.name", target = "name")
    @Mapping(source = "param.connectionUrl", target = "connectionUrl")
    @Mapping(source = "param.sseEndpoint", target = "sseEndpoint")
    @Mapping(source = "param.enabled", target = "enabled")
    @Mapping(source = "param.description", target = "description")
    McpServerEntity toUpdateEntity(McpServerEntity entity, UpdateMcpServerParam param);

    PageDTO<McpServerVO> toPage(PageDTO<McpServerEntity> page);

    default List<McpToolVO> toListTool(List<McpSchema.Tool> tools) {
        if (tools == null || tools.isEmpty()) {
            return List.of();
        }
        List<McpToolVO> mcpTools = new ArrayList<>(tools.size());
        for (McpSchema.Tool tool : tools) {
            List<McpToolPropertyVO> properties = new ArrayList<>();
            McpSchema.JsonSchema schema = tool.inputSchema();
            if (Objects.nonNull(schema) && CollUtil.isNotEmpty(schema.properties())) {
                for (Map.Entry<String, Object> map : schema.properties().entrySet()) {
                    McpToolPropertyVO property = McpToolPropertyVO.builder()
                            .name(map.getKey())
                            .build();
                    if (Objects.nonNull(map.getValue())) {
                        if (map.getValue() instanceof Map<?, ?> val) {
                            Map<String, Object> valMap = val.entrySet().stream()
                                    .collect(HashMap::new,
                                            (m, e) -> m.put(String.valueOf(e.getKey()), e.getValue()),
                                            HashMap::putAll);
                            property.setDescription(Convert.toStr(valMap.get("description")));
                        }
                    }
                    properties.add(property);
                }
            }
            mcpTools.add(McpToolVO.builder()
                    .name(tool.name())
                    .description(tool.description())
                    .properties(properties)
                    .build());
        }
        return mcpTools;
    }
}
