package cn.hengzq.orange.ai.core.biz.workflow.service.impl;

import cn.hengzq.orange.ai.common.biz.app.vo.AppVersionVO;
import cn.hengzq.orange.ai.common.biz.app.vo.param.AppVersionPageParam;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowVersionStatusEnum;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowVersionVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.AddWorkflowVersionParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.UpdateWorkflowVersionParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowVersionListParam;
import cn.hengzq.orange.ai.core.biz.workflow.converter.WorkflowVersionConverter;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowEntity;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowVersionEntity;
import cn.hengzq.orange.ai.core.biz.workflow.mapper.WorkflowMapper;
import cn.hengzq.orange.ai.core.biz.workflow.mapper.WorkflowVersionMapper;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowVersionService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.common.util.CollUtils;
import cn.hengzq.orange.context.GlobalContextHelper;
import cn.hengzq.orange.mybatis.entity.BaseEntity;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
public class WorkflowVersionServiceImpl implements WorkflowVersionService {

    private final WorkflowMapper workflowMapper;

    private final WorkflowVersionMapper workflowVersionMapper;


    @Override
    public String add(AddWorkflowVersionParam param) {
        WorkflowVersionEntity entity = WorkflowVersionConverter.INSTANCE.toEntity(param);
        entity.setVersionStatus(WorkflowVersionStatusEnum.DRAFT);
        return workflowVersionMapper.insertOne(entity);
    }

    @Override
    public List<WorkflowVersionVO> list(WorkflowVersionListParam param) {
        List<WorkflowVersionEntity> entityList = workflowVersionMapper.selectList(
                CommonWrappers.<WorkflowVersionEntity>lambdaQuery()
                        .eqIfPresent(WorkflowVersionEntity::getWorkflowId, param.getWorkflowId())
                        .likeIfPresent(WorkflowVersionEntity::getName, param.getName())
                        .orderByDesc(BaseEntity::getCreatedAt)
        );
        return WorkflowVersionConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public Map<String, WorkflowVersionVO> mapByIds(List<String> ids) {
        List<WorkflowVersionEntity> entityList = workflowVersionMapper.selectBatchIds(ids);
        if (CollUtil.isEmpty(entityList)) {
            return Map.of();
        }
        List<WorkflowVersionVO> list = WorkflowVersionConverter.INSTANCE.toListVO(entityList);
        return CollUtils.convertMap(list, WorkflowVersionVO::getId, Function.identity());
    }

    @Override
    public WorkflowVersionVO getLatestByAppId(String appId, boolean latest) {
        WorkflowVersionEntity entity;
        if (latest) {
            entity = workflowVersionMapper.selectLatestReleasedOneByAppId(appId);
        } else {
            entity = workflowVersionMapper.selectSnapshotOneByAppId(appId);
            if (Objects.isNull(entity)) {
                entity = workflowVersionMapper.selectLatestReleasedOneByAppId(appId);
            }
        }
        return Objects.isNull(entity) ? null : WorkflowVersionConverter.INSTANCE.toVO(entity);
    }

    @Override
    public WorkflowVersionVO getOrCreateSnapshotVersionByAppId(String appId) {
        WorkflowVersionEntity entity = workflowVersionMapper.selectSnapshotOneByAppId(appId);
        if (Objects.nonNull(entity)) {
            return WorkflowVersionConverter.INSTANCE.toVO(entity);
        }
//        entity = new AppVersionEntity(appId, VersionConstant.SNAPSHOT);
//        String id = appVersionMapper.insertOne(new AppVersionEntity(appId, VersionConstant.SNAPSHOT));
//        entity.setId(id);
        return WorkflowVersionConverter.INSTANCE.toVO(entity);
    }

    @Override
    public Boolean updatePublishByAppId(String appId) {
        WorkflowVersionEntity entity = workflowVersionMapper.selectSnapshotOneByAppId(appId);
        if (Objects.isNull(entity)) {
            return true;
        }
        WorkflowVersionEntity latestEntity = workflowVersionMapper.selectLatestReleasedOneByAppId(appId);
        if (Objects.nonNull(latestEntity)) {
//            latestEntity.setLatestReleased(false);
            workflowVersionMapper.updateById(latestEntity);
        }
//        entity.setVersion(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
//        entity.setLatestReleased(true);
        workflowVersionMapper.updateById(entity);
        return true;
    }

    @Override
    public Boolean updateById(String id, UpdateWorkflowVersionParam param) {
        WorkflowVersionEntity entity = workflowVersionMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);

        entity = WorkflowVersionConverter.INSTANCE.toUpdate(entity, param);
        workflowVersionMapper.updateById(entity);
        return Boolean.TRUE;
    }

    @Override
    public WorkflowVersionVO createDraftFromPublished(String id) {
        WorkflowVersionEntity entity = workflowVersionMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);

        WorkflowVersionEntity draftEntity = new WorkflowVersionEntity();
        draftEntity.setWorkflowId(entity.getWorkflowId());
        draftEntity.setName(entity.getName());
        draftEntity.setDescription(entity.getDescription());
        draftEntity.setVersionStatus(WorkflowVersionStatusEnum.DRAFT);
        workflowVersionMapper.insertOne(draftEntity);

        return WorkflowVersionConverter.INSTANCE.toVO(draftEntity);
    }

    @Override
    public WorkflowVersionVO getWorkflowVersionById(String id) {
        return WorkflowVersionConverter.INSTANCE.toVO(workflowVersionMapper.selectById(id));
    }

    @Override
    public Boolean updatePublishById(String id) {
        WorkflowVersionEntity entity = workflowVersionMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);

        entity.setPublishAt(LocalDateTime.now());
        entity.setPublishBy(GlobalContextHelper.getUserId());
        entity.setVersionStatus(WorkflowVersionStatusEnum.PUBLISHED);
        workflowVersionMapper.updateById(entity);
        return Boolean.TRUE;
    }

    @Override
    public PageDTO<AppVersionVO> page(AppVersionPageParam param) {
        PageDTO<WorkflowVersionEntity> page = workflowVersionMapper.selectPage(param, CommonWrappers.<WorkflowVersionEntity>lambdaQuery()
                .eq(WorkflowVersionEntity::getWorkflowId, param.getAppId())
                .orderByDesc(BaseEntity::getCreatedAt)
        );
        if (Objects.isNull(page) || CollUtil.isEmpty(page.getRecords())) {
            return PageDTO.of(param.getPageNo(), param.getPageSize());
        }
        return WorkflowVersionConverter.INSTANCE.toPage(page);
    }

    @Override
    public List<WorkflowVersionVO> listByAppId(String appId) {
        List<WorkflowVersionEntity> entityList = workflowVersionMapper.selectList(
                CommonWrappers.<WorkflowVersionEntity>lambdaQuery()
                        .eq(WorkflowVersionEntity::getWorkflowId, appId)
                        .orderByDesc(BaseEntity::getCreatedAt)
        );
        return WorkflowVersionConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public Boolean deleteByAppId(String appId) {
        int delete = workflowVersionMapper.delete(CommonWrappers.<WorkflowVersionEntity>lambdaQuery()
                .eq(WorkflowVersionEntity::getWorkflowId, appId));
        return delete > 0;
    }

    @Override
    public WorkflowVersionVO getById(String id) {
        WorkflowVersionEntity entity = workflowVersionMapper.selectById(id);
        return WorkflowVersionConverter.INSTANCE.toVO(entity);
    }

    @Override
    public WorkflowVersionVO getOrCreateDraftByWorkflowId(String workflowId) {
        WorkflowEntity entity = workflowMapper.selectById(workflowId);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);

        // 存在草稿版，直接更新。
        String draftVersionId = entity.getDraftVersionId();
        if (StrUtil.isBlank(draftVersionId)) {
            // 不存在创建草稿版
            return this.createDraftFromPublished(entity.getPublishedVersionId());
        }
        return this.getWorkflowVersionById(draftVersionId);
    }


}
