package cn.hengzq.orange.ai.core.biz.workflow.mapper;

import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowVersionEntity;
import cn.hengzq.orange.mybatis.mapper.CommonMapper;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author hengzq
 */
@Repository
public interface WorkflowVersionMapper extends CommonMapper<WorkflowVersionEntity> {


    default WorkflowVersionEntity selectLatestReleasedOneByAppId(String appId) {
        return this.selectOne(
                CommonWrappers.<WorkflowVersionEntity>lambdaQuery().eq(WorkflowVersionEntity::getWorkflowId, appId)
//                        .eq(AppVersionEntity::isLatestReleased, true)
        );
    }

    default WorkflowVersionEntity selectSnapshotOneByAppId(String appId) {
        return this.selectOne(
                CommonWrappers.<WorkflowVersionEntity>lambdaQuery().eq(WorkflowVersionEntity::getWorkflowId, appId)
//                        .eq(AppVersionEntity::getVersion, VersionConstant.SNAPSHOT)
        );
    }

    default List<WorkflowVersionEntity> selectByAppIds(List<String> appIds) {
        if (CollUtil.isEmpty(appIds)) {
            return Collections.emptyList();
        }
        return this.selectList(CommonWrappers.<WorkflowVersionEntity>lambdaQuery()
                .in(WorkflowVersionEntity::getWorkflowId, appIds)
        );
    }
}
