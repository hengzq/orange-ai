package cn.hengzq.orange.ai.core.biz.workflow.converter;


import cn.hengzq.orange.ai.common.biz.app.vo.AppVO;
import cn.hengzq.orange.ai.common.biz.app.vo.param.AddAppParam;
import cn.hengzq.orange.ai.common.biz.app.vo.param.UpdateAppParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowEdgeVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.CreateWorkflowEdgeParam;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowEdgeEntity;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowEntity;
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
public interface WorkflowEdgeConverter extends Converter {

    WorkflowEdgeConverter INSTANCE = Mappers.getMapper(WorkflowEdgeConverter.class);

    WorkflowEntity toEntity(AppVO modelVO);

    WorkflowEntity toEntity(AddAppParam request);

    WorkflowEdgeVO toVO(WorkflowEdgeEntity entity);

    List<WorkflowEdgeVO> toListVO(List<WorkflowEdgeEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    WorkflowEntity toUpdateEntity(WorkflowEntity entity, UpdateAppParam param);

    PageDTO<WorkflowVO> toPage(PageDTO<WorkflowEntity> page);

    @Mapping(source = "workflowVersionId", target = "workflowVersionId")
    @Mapping(source = "param.workflowId", target = "workflowId")
    @Mapping(source = "param.sourceNodeId", target = "sourceNodeId")
    @Mapping(source = "param.targetNodeId", target = "targetNodeId")
    WorkflowEdgeEntity toEntity(String workflowVersionId, CreateWorkflowEdgeParam param);

    List<WorkflowEdgeEntity> toListEntity(List<WorkflowEdgeVO> edges);
}
