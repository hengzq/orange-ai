package cn.hengzq.orange.ai.core.biz.workflow.service;

import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowRunDetailVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowRunVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowCreateRequest;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.CreateWorkflowRunParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowRunParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.result.WorkflowRunResult;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowNodeRunEntity;

public interface WorkflowRunService {

    WorkflowRunVO create(CreateWorkflowRunParam param);

    void update(WorkflowNodeRunEntity entity);

    /**
     * 重新执行一个已存在的执行实例（Run）
     */
    void rerun(String id);

    String executeWorkflow();

    String invokeLlmNode(WorkflowCreateRequest param);

    WorkflowRunResult runWorkflowById(String id, WorkflowRunParam param);

    /**
     * 异步启动一个工作流的新执行实例
     */
    WorkflowRunResult runWorkflowAsync(String workflowId, WorkflowRunParam param);

    /**
     * 异步启动一个工作流中的某一个节点
     */
    WorkflowRunVO runSingleNodeAsync(String workflowId, String nodeId, WorkflowRunParam param);

    /**
     * 同步启动一个工作流中的某一个节点
     */
    WorkflowRunVO runSingleNodeSync(String workflowId, String nodeId, WorkflowRunParam param);


    WorkflowRunDetailVO getDetailById(String id);
}
