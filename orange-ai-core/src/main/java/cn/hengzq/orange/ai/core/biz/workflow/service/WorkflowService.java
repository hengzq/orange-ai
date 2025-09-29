package cn.hengzq.orange.ai.core.biz.workflow.service;


import cn.hengzq.orange.ai.common.biz.app.dto.request.AppListParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowDetailVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowListResponse;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.UpdateWorkflowGraphParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowCreateRequest;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowPageRequest;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowUpdateRequest;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;

/**
 * @author hengzq
 */
public interface WorkflowService {

    String createWorkflow(WorkflowCreateRequest param);

    void deleteWorkflowById(String id);

    Boolean updateById(String id, WorkflowUpdateRequest request);

    Boolean updateGraphById(String id, UpdateWorkflowGraphParam param);

    Boolean updatePublishById(String id);

    WorkflowVO getById(String id);

    PageDTO<WorkflowListResponse> pageWorkflows(WorkflowPageRequest request);

    List<WorkflowVO> list(AppListParam query);

    WorkflowVO getById(String id, boolean latestReleased);

    WorkflowDetailVO getDetailById(String id, boolean latestReleased);

    WorkflowDetailVO getDetailByIdAndVersionId(String id, String versionId);

}
