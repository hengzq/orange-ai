package cn.hengzq.orange.ai.core.biz.workflow.service;


import cn.hengzq.orange.ai.common.biz.app.vo.AppVersionVO;
import cn.hengzq.orange.ai.common.biz.app.vo.param.AppVersionPageParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowVersionVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.AddWorkflowVersionParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.UpdateWorkflowVersionParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowVersionListParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;
import java.util.Map;

/**
 * @author hengzq
 */
public interface WorkflowVersionService {

    String add(AddWorkflowVersionParam param);

    List<WorkflowVersionVO> list(WorkflowVersionListParam param);

    Map<String, WorkflowVersionVO> mapByIds(List<String> ids);

    WorkflowVersionVO getLatestByAppId(String appId, boolean latest);

    /**
     * 根据应用ID，获取或创建一个快照版本
     *
     * @param appId
     * @return 快照版本
     */
    WorkflowVersionVO getOrCreateSnapshotVersionByAppId(String appId);

    Boolean updatePublishByAppId(String appId);

    Boolean updateById(String id, UpdateWorkflowVersionParam param);

    WorkflowVersionVO createDraftFromPublished(String id);

    WorkflowVersionVO getWorkflowVersionById(String id);

    Boolean updatePublishById(String draftVersionId);

    PageDTO<AppVersionVO> page(AppVersionPageParam param);

    List<WorkflowVersionVO> listByAppId(String appId);

    Boolean deleteByAppId(String appId);

    WorkflowVersionVO getById(String id);

    WorkflowVersionVO getOrCreateDraftByWorkflowId(String workflowId);
}
