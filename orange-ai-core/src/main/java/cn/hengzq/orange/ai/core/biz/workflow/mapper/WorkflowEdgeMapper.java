package cn.hengzq.orange.ai.core.biz.workflow.mapper;

import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowEdgeEntity;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowNodeEntity;
import cn.hengzq.orange.mybatis.mapper.CommonMapper;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Repository;

/**
 * @author hengzq
 */
@Repository
public interface WorkflowEdgeMapper extends CommonMapper<WorkflowEdgeEntity> {


    default void deleteByWorkflowVersionId(String workflowVersionId) {
        if (StrUtil.isBlank(workflowVersionId)) {
            return;
        }
        this.delete(CommonWrappers.<WorkflowEdgeEntity>lambdaQuery()
                .eq(WorkflowEdgeEntity::getWorkflowVersionId, workflowVersionId));
    }
}
