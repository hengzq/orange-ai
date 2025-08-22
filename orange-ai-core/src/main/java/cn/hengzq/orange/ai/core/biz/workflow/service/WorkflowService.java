package cn.hengzq.orange.ai.core.biz.workflow.service;


import cn.hengzq.orange.ai.common.biz.app.vo.param.*;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowDetailVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.AddWorkflowParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.UpdateWorkflowGraphParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.UpdateWorkflowParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;

/**
 * @author hengzq
 */
public interface WorkflowService {

    String create(AddWorkflowParam param);

    Boolean removeById(String id);

    Boolean updateById(String id, UpdateWorkflowParam request);

    Boolean updateGraphById(String id, UpdateWorkflowGraphParam param);

    Boolean updatePublishById(String id);

    WorkflowVO getById(String id);

    List<WorkflowVO> list(AppListParam query);

    PageDTO<WorkflowVO> page(WorkflowPageParam param);

    WorkflowVO getById(String id, boolean latestReleased);

    WorkflowDetailVO getDetailById(String id, boolean latestReleased);

    WorkflowDetailVO getDetailByIdAndVersionId(String id, String versionId);

}
