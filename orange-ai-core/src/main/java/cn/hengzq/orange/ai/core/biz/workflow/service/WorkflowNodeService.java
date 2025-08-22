package cn.hengzq.orange.ai.core.biz.workflow.service;


import cn.hengzq.orange.ai.common.biz.app.vo.param.WorkflowPageParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowNodeVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.AddWorkflowParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.CreateWorkflowNodeParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.UpdateWorkflowParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.WorkflowNodeListParam;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowNodeRunEntity;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;

/**
 * @author hengzq
 */
public interface WorkflowNodeService {

    String add(AddWorkflowParam param);

    Boolean removeById(String id);

    Boolean updateById(String id, UpdateWorkflowParam request);

    Boolean updatePublishById(String id);

    WorkflowNodeVO getById(String id);

    PageDTO<WorkflowVO> page(WorkflowPageParam param);

    WorkflowNodeVO create(CreateWorkflowNodeParam param);

    List<WorkflowNodeVO> list(WorkflowNodeListParam param);

    void createStartAndEndNodes(String workflowId, String workflowVersionId);

    void replaceByWorkflowVersionId(String workflowVersionId, List<WorkflowNodeVO> nodes);


}
