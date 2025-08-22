package cn.hengzq.orange.ai.core.biz.workflow.mapper;

import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowNodeEntity;
import cn.hengzq.orange.mybatis.mapper.CommonMapper;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import org.springframework.stereotype.Repository;

/**
 * @author hengzq
 */
@Repository
public interface WorkflowNodeMapper extends CommonMapper<WorkflowNodeEntity> {


    default void deleteByWorkflowVersionId(String workflowVersionId) {
        this.delete(CommonWrappers.<WorkflowNodeEntity>lambdaQuery()
                .eq(WorkflowNodeEntity::getWorkflowVersionId, workflowVersionId));
    }
}
