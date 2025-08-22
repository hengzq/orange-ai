package cn.hengzq.orange.ai.core.biz.workflow.service;


import cn.hengzq.orange.ai.common.biz.app.vo.param.WorkflowPageParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowEdgeVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowNodeVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.AddWorkflowParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.CreateWorkflowEdgeParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.UpdateWorkflowParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.WorkflowNodeListParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;

/**
 * @author hengzq
 */
public interface WorkflowEdgeService {

    String add(AddWorkflowParam param);

    Boolean removeById(String id);

    Boolean updateById(String id, UpdateWorkflowParam request);

    Boolean updatePublishById(String id);

    WorkflowVO getById(String id);

    PageDTO<WorkflowVO> page(WorkflowPageParam param);

    WorkflowEdgeVO create(CreateWorkflowEdgeParam param);

    List<WorkflowNodeVO> list(WorkflowNodeListParam param);

    void createStartAndEndNodes(String workflowId, String workflowVersionId);

    List<WorkflowEdgeVO> listByWorkflowVersionId(String workflowVersionId);

    void replaceByWorkflowVersionId(String workflowVersionId, List<WorkflowEdgeVO> edges);
}
