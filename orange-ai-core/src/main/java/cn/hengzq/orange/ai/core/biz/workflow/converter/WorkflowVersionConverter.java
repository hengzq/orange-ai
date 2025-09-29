package cn.hengzq.orange.ai.core.biz.workflow.converter;


import cn.hengzq.orange.ai.common.biz.app.dto.AppVersionVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowVersionVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.AddWorkflowVersionParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowUpdateRequest;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.UpdateWorkflowVersionParam;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowVersionEntity;
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
public interface WorkflowVersionConverter extends Converter {

    WorkflowVersionConverter INSTANCE = Mappers.getMapper(WorkflowVersionConverter.class);

    WorkflowVersionVO toVO(WorkflowVersionEntity entity);

    WorkflowVersionEntity toEntity(AddWorkflowVersionParam param);

    List<WorkflowVersionVO> toListVO(List<WorkflowVersionEntity> entityList);

    UpdateWorkflowVersionParam toUpdate(WorkflowUpdateRequest param);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "param.name", target = "name")
    @Mapping(source = "param.description", target = "description")
    WorkflowVersionEntity toUpdate(WorkflowVersionEntity entity, UpdateWorkflowVersionParam param);

    PageDTO<AppVersionVO> toPage(PageDTO<WorkflowVersionEntity> page);
}
