package cn.hengzq.orange.ai.core.biz.workflow.service;


import cn.hengzq.orange.ai.common.biz.app.dto.request.AppPageRequest;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowEdgeVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowNodeVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowCreateRequest;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.CreateWorkflowEdgeParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowUpdateRequest;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowNodeListParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;

/**
 * @author hengzq
 */
public interface WorkflowEdgeService {

    String add(WorkflowCreateRequest param);

    Boolean removeById(String id);

    Boolean updateById(String id, WorkflowUpdateRequest request);

    Boolean updatePublishById(String id);

    WorkflowVO getById(String id);

    PageDTO<WorkflowVO> page(AppPageRequest param);

    WorkflowEdgeVO create(CreateWorkflowEdgeParam param);

    List<WorkflowNodeVO> list(WorkflowNodeListParam param);

    void createStartAndEndNodes(String workflowId, String workflowVersionId);

    List<WorkflowEdgeVO> listByWorkflowVersionId(String workflowVersionId);

    void replaceByWorkflowVersionId(String workflowVersionId, List<WorkflowEdgeVO> edges);
}
