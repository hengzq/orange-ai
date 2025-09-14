package cn.hengzq.orange.ai.core.biz.workflow.converter;


import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowRunNodeVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.CreateWorkflowRunNodeParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.UpdateWorkflowRunNodeParam;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowNodeRunEntity;
import cn.hengzq.orange.common.converter.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hengzq
 */
@Mapper
public interface WorkflowRunNodeConverter extends Converter {

    WorkflowRunNodeConverter INSTANCE = Mappers.getMapper(WorkflowRunNodeConverter.class);


    WorkflowNodeRunEntity toEntity(CreateWorkflowRunNodeParam param);

    WorkflowRunNodeVO toVO(WorkflowNodeRunEntity entity);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "param.runStatus", target = "runStatus")
    @Mapping(source = "param.outputData", target = "outputData")
    WorkflowNodeRunEntity toUpdateEntity(WorkflowNodeRunEntity entity, UpdateWorkflowRunNodeParam param);

    List<WorkflowRunNodeVO> toList(List<WorkflowNodeRunEntity> entityList);
}
