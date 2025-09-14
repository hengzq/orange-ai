package cn.hengzq.orange.ai.core.biz.workflow.service;

import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowRunNodeVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.CreateWorkflowRunNodeParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.UpdateWorkflowRunNodeParam;

import java.util.List;

public interface WorkflowRunNodeService {

    WorkflowRunNodeVO create(CreateWorkflowRunNodeParam param);


    boolean updateById(String id, UpdateWorkflowRunNodeParam param);

    List<WorkflowRunNodeVO> listByRunId(String runId);
}
