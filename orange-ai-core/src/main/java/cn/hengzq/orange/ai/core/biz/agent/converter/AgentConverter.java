package cn.hengzq.orange.ai.core.biz.agent.converter;


import cn.hengzq.orange.ai.common.biz.agent.vo.AgentVO;
import cn.hengzq.orange.ai.common.biz.agent.vo.param.AddAgentParam;
import cn.hengzq.orange.ai.common.biz.agent.vo.param.UpdateAgentParam;
import cn.hengzq.orange.ai.core.biz.agent.entity.AgentEntity;
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
public interface AgentConverter extends Converter {

    AgentConverter INSTANCE = Mappers.getMapper(AgentConverter.class);

    AgentEntity toEntity(AgentVO modelVO);

    AgentEntity toEntity(AddAgentParam request);

    AgentVO toVO(AgentEntity entity);

    List<AgentVO> toListV0(List<AgentEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "param.name", target = "name")
    @Mapping(source = "param.modelId", target = "modelId")
    @Mapping(source = "param.modelConfig", target = "modelConfig")
    @Mapping(source = "param.baseIds", target = "baseIds")
    @Mapping(source = "param.systemPrompt", target = "systemPrompt")
    @Mapping(source = "param.description", target = "description")
    AgentEntity toUpdateEntity(AgentEntity entity, UpdateAgentParam param);

    PageDTO<AgentVO> toPage(PageDTO<AgentEntity> page);
}
