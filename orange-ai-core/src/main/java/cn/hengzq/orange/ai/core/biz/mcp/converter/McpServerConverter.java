package cn.hengzq.orange.ai.core.biz.mcp.converter;


import cn.hengzq.orange.ai.common.biz.mcp.vo.McpServerVO;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.AddMcpServerParam;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.UpdateMcpServerParam;
import cn.hengzq.orange.ai.core.biz.mcp.entity.McpServerEntity;
import cn.hengzq.orange.common.converter.Converter;
import cn.hengzq.orange.common.dto.PageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

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
}
