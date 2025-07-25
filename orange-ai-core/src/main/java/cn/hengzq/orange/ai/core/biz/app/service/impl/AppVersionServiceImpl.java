package cn.hengzq.orange.ai.core.biz.app.service.impl;

import cn.hengzq.orange.ai.common.biz.app.constant.AppVersionStatusEnum;
import cn.hengzq.orange.ai.common.biz.app.vo.AppVersionVO;
import cn.hengzq.orange.ai.common.biz.app.vo.param.AddAppVersionParam;
import cn.hengzq.orange.ai.common.biz.app.vo.param.AppVersionListParam;
import cn.hengzq.orange.ai.common.biz.app.vo.param.AppVersionPageParam;
import cn.hengzq.orange.ai.common.biz.app.vo.param.UpdateAppVersionParam;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.core.biz.app.converter.AppVersionConverter;
import cn.hengzq.orange.ai.core.biz.app.entity.AppVersionEntity;
import cn.hengzq.orange.ai.core.biz.app.mapper.AppVersionMapper;
import cn.hengzq.orange.ai.core.biz.app.service.AppVersionService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.common.util.CollUtils;
import cn.hengzq.orange.context.GlobalContextHelper;
import cn.hengzq.orange.mybatis.entity.BaseEntity;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class AppVersionServiceImpl implements AppVersionService {

    private final AppVersionMapper appVersionMapper;


    @Override
    public String add(AddAppVersionParam param) {
        AppVersionEntity entity = AppVersionConverter.INSTANCE.toEntity(param);
        entity.setVersionStatus(AppVersionStatusEnum.DRAFT);
        return appVersionMapper.insertOne(entity);
    }

    @Override
    public List<AppVersionVO> list(AppVersionListParam param) {
        List<AppVersionEntity> entityList = appVersionMapper.selectList(
                CommonWrappers.<AppVersionEntity>lambdaQuery()
                        .likeIfPresent(AppVersionEntity::getName, param.getName())
                        .orderByDesc(BaseEntity::getCreatedAt)
        );
        return AppVersionConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public Map<String, AppVersionVO> mapByIds(List<String> ids) {
        List<AppVersionEntity> entityList = appVersionMapper.selectBatchIds(ids);
        if (CollUtil.isEmpty(entityList)) {
            return Map.of();
        }
        List<AppVersionVO> list = AppVersionConverter.INSTANCE.toListVO(entityList);
        return CollUtils.convertMap(list, AppVersionVO::getId, Function.identity());
    }

    @Override
    public AppVersionVO getLatestByAppId(String appId, boolean latest) {
        AppVersionEntity entity;
        if (latest) {
            entity = appVersionMapper.selectLatestReleasedOneByAppId(appId);
        } else {
            entity = appVersionMapper.selectSnapshotOneByAppId(appId);
            if (Objects.isNull(entity)) {
                entity = appVersionMapper.selectLatestReleasedOneByAppId(appId);
            }
        }
        return Objects.isNull(entity) ? null : AppVersionConverter.INSTANCE.toVO(entity);
    }

    @Override
    public AppVersionVO getOrCreateSnapshotVersionByAppId(String appId) {
        AppVersionEntity entity = appVersionMapper.selectSnapshotOneByAppId(appId);
        if (Objects.nonNull(entity)) {
            return AppVersionConverter.INSTANCE.toVO(entity);
        }
//        entity = new AppVersionEntity(appId, VersionConstant.SNAPSHOT);
//        String id = appVersionMapper.insertOne(new AppVersionEntity(appId, VersionConstant.SNAPSHOT));
//        entity.setId(id);
        return AppVersionConverter.INSTANCE.toVO(entity);
    }

    @Override
    public Boolean updatePublishByAppId(String appId) {
        AppVersionEntity entity = appVersionMapper.selectSnapshotOneByAppId(appId);
        if (Objects.isNull(entity)) {
            return true;
        }
        AppVersionEntity latestEntity = appVersionMapper.selectLatestReleasedOneByAppId(appId);
        if (Objects.nonNull(latestEntity)) {
//            latestEntity.setLatestReleased(false);
            appVersionMapper.updateById(latestEntity);
        }
//        entity.setVersion(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
//        entity.setLatestReleased(true);
        appVersionMapper.updateById(entity);
        return true;
    }

    @Override
    public Boolean updateById(String id, UpdateAppVersionParam param) {
        AppVersionEntity entity = appVersionMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);

        entity = AppVersionConverter.INSTANCE.toUpdate(entity, param);
        appVersionMapper.updateById(entity);
        return Boolean.TRUE;
    }

    @Override
    public String addDraftByPublishedId(String id) {
        AppVersionEntity entity = appVersionMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);

        AppVersionEntity draftEntity = new AppVersionEntity();
        draftEntity.setAppId(entity.getAppId());
        draftEntity.setName(entity.getName());
        draftEntity.setSystemPrompt(entity.getSystemPrompt());
        draftEntity.setDescription(entity.getDescription());
        draftEntity.setModelId(entity.getModelId());
        draftEntity.setModelConfig(entity.getModelConfig());
        draftEntity.setBaseIds(entity.getBaseIds());
        draftEntity.setBaseConfig(entity.getBaseConfig());
        draftEntity.setMcpIds(entity.getMcpIds());
        draftEntity.setVersionStatus(AppVersionStatusEnum.DRAFT);
        return appVersionMapper.insertOne(draftEntity);
    }

    @Override
    public AppVersionVO getById(String id) {
        return AppVersionConverter.INSTANCE.toVO(appVersionMapper.selectById(id));
    }

    @Override
    public Boolean updatePublishById(String id) {
        AppVersionEntity entity = appVersionMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);

        entity.setPublishAt(LocalDateTime.now());
        entity.setPublishBy(GlobalContextHelper.getUserId());
        entity.setVersionStatus(AppVersionStatusEnum.PUBLISHED);
        appVersionMapper.updateById(entity);
        return true;
    }

    @Override
    public PageDTO<AppVersionVO> page(AppVersionPageParam param) {
        PageDTO<AppVersionEntity> page = appVersionMapper.selectPage(param, CommonWrappers.<AppVersionEntity>lambdaQuery()
                .eq(AppVersionEntity::getAppId, param.getAppId())
                .orderByDesc(BaseEntity::getCreatedAt)
        );
        if (Objects.isNull(page) || CollUtil.isEmpty(page.getRecords())) {
            return PageDTO.of(param.getPageNo(), param.getPageSize());
        }
        return AppVersionConverter.INSTANCE.toPage(page);
    }

    @Override
    public List<AppVersionVO> listByAppId(String appId) {
        List<AppVersionEntity> entityList = appVersionMapper.selectList(
                CommonWrappers.<AppVersionEntity>lambdaQuery()
                        .eq(AppVersionEntity::getAppId, appId)
                        .orderByDesc(BaseEntity::getCreatedAt)
        );
        return AppVersionConverter.INSTANCE.toListVO(entityList);
    }


}
