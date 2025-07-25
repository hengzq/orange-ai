package cn.hengzq.orange.ai.core.biz.app.mapper;

import cn.hengzq.orange.ai.core.biz.app.entity.AppVersionEntity;
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
public interface AppVersionMapper extends CommonMapper<AppVersionEntity> {


    default AppVersionEntity selectLatestReleasedOneByAppId(String appId) {
        return this.selectOne(
                CommonWrappers.<AppVersionEntity>lambdaQuery().eq(AppVersionEntity::getAppId, appId)
//                        .eq(AppVersionEntity::isLatestReleased, true)
        );
    }

    default AppVersionEntity selectSnapshotOneByAppId(String appId) {
        return this.selectOne(
                CommonWrappers.<AppVersionEntity>lambdaQuery().eq(AppVersionEntity::getAppId, appId)
//                        .eq(AppVersionEntity::getVersion, VersionConstant.SNAPSHOT)
        );
    }

    default List<AppVersionEntity> selectByAppIds(List<String> appIds) {
        if (CollUtil.isEmpty(appIds)) {
            return Collections.emptyList();
        }
        return this.selectList(CommonWrappers.<AppVersionEntity>lambdaQuery()
                .in(AppVersionEntity::getAppId, appIds)
        );
    }
}
