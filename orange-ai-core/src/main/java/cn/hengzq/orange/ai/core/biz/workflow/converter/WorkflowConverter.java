package cn.hengzq.orange.ai.core.biz.workflow.converter;


import cn.hengzq.orange.ai.common.biz.app.dto.AppVO;
import cn.hengzq.orange.ai.common.biz.app.dto.request.AppUpdateRequest;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowDetailVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowVO;
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
public interface WorkflowConverter extends Converter {

    WorkflowConverter INSTANCE = Mappers.getMapper(WorkflowConverter.class);

    WorkflowEntity toEntity(AppVO modelVO);

    WorkflowVO toVO(WorkflowEntity entity);

    List<WorkflowVO> toListVO(List<WorkflowEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    WorkflowEntity toUpdateEntity(WorkflowEntity entity, AppUpdateRequest param);

    PageDTO<WorkflowVO> toPage(PageDTO<WorkflowEntity> page);

    WorkflowDetailVO toDetailVO(WorkflowEntity workflowEntity);
}
