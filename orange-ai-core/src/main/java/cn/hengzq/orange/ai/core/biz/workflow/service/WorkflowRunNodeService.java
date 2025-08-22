package cn.hengzq.orange.ai.core.biz.workflow.service;

import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowRunNodeVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.CreateWorkflowRunNodeParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.UpdateWorkflowRunNodeParam;

import java.util.List;

public interface WorkflowRunNodeService {

    WorkflowRunNodeVO create(CreateWorkflowRunNodeParam param);


    boolean updateById(String id, UpdateWorkflowRunNodeParam param);

    List<WorkflowRunNodeVO> listByRunId(String runId);
}
