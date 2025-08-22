package cn.hengzq.orange.ai.core.biz.workflow.converter;


import cn.hengzq.orange.ai.common.biz.app.vo.AppVO;
import cn.hengzq.orange.ai.common.biz.app.vo.param.AddAppParam;
import cn.hengzq.orange.ai.common.biz.app.vo.param.UpdateAppParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowDetailVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowVO;
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

    WorkflowEntity toEntity(AddAppParam request);

    WorkflowVO toVO(WorkflowEntity entity);

    List<WorkflowVO> toListVO(List<WorkflowEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    WorkflowEntity toUpdateEntity(WorkflowEntity entity, UpdateAppParam param);

    PageDTO<WorkflowVO> toPage(PageDTO<WorkflowEntity> page);

    WorkflowDetailVO toDetailVO(WorkflowEntity workflowEntity);
}
