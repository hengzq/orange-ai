package cn.hengzq.orange.ai.core.biz.workflow.converter;


import cn.hengzq.orange.ai.common.biz.app.vo.AppVO;
import cn.hengzq.orange.ai.common.biz.app.vo.param.AddAppParam;
import cn.hengzq.orange.ai.common.biz.app.vo.param.UpdateAppParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowNodeVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.CreateWorkflowNodeParam;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowEntity;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowNodeEntity;
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
public interface WorkflowNodeConverter extends Converter {

    WorkflowNodeConverter INSTANCE = Mappers.getMapper(WorkflowNodeConverter.class);

    WorkflowEntity toEntity(AppVO modelVO);

    WorkflowEntity toEntity(AddAppParam request);

    WorkflowNodeVO toVO(WorkflowNodeEntity entity);

    List<WorkflowNodeVO> toListVO(List<WorkflowNodeEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    WorkflowEntity toUpdateEntity(WorkflowEntity entity, UpdateAppParam param);

    PageDTO<WorkflowVO> toPage(PageDTO<WorkflowEntity> page);

    @Mapping(source = "workflowVersionId", target = "workflowVersionId")
    @Mapping(source = "param.workflowId", target = "workflowId")
    @Mapping(source = "param.nodeType", target = "nodeType")
    @Mapping(source = "param.position", target = "position")
    WorkflowNodeEntity toEntity(String workflowVersionId, CreateWorkflowNodeParam param);

    List<WorkflowNodeEntity> toListEntity(List<WorkflowNodeVO> nodes);
}
