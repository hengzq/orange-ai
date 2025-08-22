package cn.hengzq.orange.ai.core.biz.workflow.service.impl;

import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowRunStatusEnum;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowRunNodeVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.CreateWorkflowRunNodeParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.UpdateWorkflowRunNodeParam;
import cn.hengzq.orange.ai.core.biz.mcp.converter.McpServerConverter;
import cn.hengzq.orange.ai.core.biz.mcp.entity.McpServerEntity;
import cn.hengzq.orange.ai.core.biz.workflow.converter.WorkflowRunNodeConverter;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowNodeRunEntity;
import cn.hengzq.orange.ai.core.biz.workflow.mapper.WorkflowNodeRunMapper;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowRunNodeService;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class WorkflowRunNodeServiceImpl implements WorkflowRunNodeService {


    private final WorkflowNodeRunMapper workflowNodeRunMapper;

    @Override
    public WorkflowRunNodeVO create(CreateWorkflowRunNodeParam param) {
        WorkflowNodeRunEntity entity = WorkflowRunNodeConverter.INSTANCE.toEntity(param);
        entity.setRunStatus(Objects.isNull(entity.getRunStatus()) ? WorkflowRunStatusEnum.PENDING : entity.getRunStatus());
        workflowNodeRunMapper.insertOne(entity);
        return WorkflowRunNodeConverter.INSTANCE.toVO(entity);
    }

    @Override
    public boolean updateById(String id, UpdateWorkflowRunNodeParam param) {
        WorkflowNodeRunEntity entity = workflowNodeRunMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);

        entity = WorkflowRunNodeConverter.INSTANCE.toUpdateEntity(entity, param);
        return workflowNodeRunMapper.updateOneById(entity);
    }

    @Override
    public List<WorkflowRunNodeVO> listByRunId(String runId) {
        if (StrUtil.isBlank(runId)){
            return Collections.emptyList();
        }
        List<WorkflowNodeRunEntity> entityList = workflowNodeRunMapper.selectList(CommonWrappers.<WorkflowNodeRunEntity>lambdaQuery()
                .eq(WorkflowNodeRunEntity::getRunId, runId));
        return WorkflowRunNodeConverter.INSTANCE.toList(entityList);
    }


}
