package cn.hengzq.orange.ai.core.biz.workflow.service.impl;

import cn.hengzq.orange.ai.common.biz.app.vo.param.AppListParam;
import cn.hengzq.orange.ai.common.biz.app.vo.param.WorkflowPageRequest;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowStatusEnum;
import cn.hengzq.orange.ai.common.biz.workflow.dto.*;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.*;
import cn.hengzq.orange.ai.core.biz.workflow.converter.WorkflowConverter;
import cn.hengzq.orange.ai.core.biz.workflow.converter.WorkflowVersionConverter;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowEntity;
import cn.hengzq.orange.ai.core.biz.workflow.mapper.WorkflowMapper;
import cn.hengzq.orange.ai.core.biz.workflow.service.*;
import cn.hengzq.orange.common.constant.PageConstant;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.mybatis.entity.BaseEntity;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowMapper workflowMapper;

    private final WorkflowVersionService workflowVersionService;

    private final WorkflowNodeService workflowNodeService;

    private final WorkflowEdgeService workflowEdgeService;


    @Override
    public void deleteWorkflowById(String id) {
        if (!workflowMapper.deleteOneById(id)) {
        }
        // 删除关联数据
//        workflowVersionService.deleteByAppId(id);
    }

    @Override
    @Transactional
    public String createWorkflow(WorkflowCreateRequest param) {
        String workflowId = IdUtil.getSnowflakeNextIdStr();
        String draftVersionId = workflowVersionService.add(AddWorkflowVersionParam.builder()
                .workflowId(workflowId)
                .name(param.getName())
                .description(param.getDescription())
                .build());
        WorkflowEntity entity = new WorkflowEntity();
        entity.setId(workflowId);
        entity.setDraftVersionId(draftVersionId);
        entity.setWorkflowStatus(WorkflowStatusEnum.DRAFT);

        // 初始化流程节点
        workflowNodeService.createStartAndEndNodes(workflowId, draftVersionId);
        return workflowMapper.insertOne(entity);
    }

    @Override
    public Boolean updateById(String id, WorkflowUpdateRequest param) {
        WorkflowEntity entity = workflowMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);

        // 存在草稿版，直接更新。
        String draftVersionId = entity.getDraftVersionId();
        if (StrUtil.isBlank(draftVersionId)) {
            // 不存在创建草稿版
            WorkflowVersionVO draft = workflowVersionService.createDraftFromPublished(entity.getPublishedVersionId());
            draftVersionId = draft.getId();
        }
        workflowVersionService.updateById(draftVersionId, WorkflowVersionConverter.INSTANCE.toUpdate(param));

        // 更新应用状态
        entity.setDraftVersionId(draftVersionId);
        WorkflowStatusEnum workflowStatus = Objects.isNull(entity.getWorkflowStatus()) ? WorkflowStatusEnum.DRAFT :
                (WorkflowStatusEnum.PUBLISHED.equals(entity.getWorkflowStatus()) ? WorkflowStatusEnum.PUBLISHED_EDITING : entity.getWorkflowStatus());
        entity.setWorkflowStatus(workflowStatus);

        return workflowMapper.updateOneById(entity);
    }

    @Override
    @Transactional
    public Boolean updateGraphById(String id, UpdateWorkflowGraphParam param) {
        WorkflowEntity entity = workflowMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        WorkflowVersionVO draft;
        boolean newDraftVersion;
        if (StrUtil.isNotBlank(entity.getDraftVersionId())) {
            newDraftVersion = false;
            draft = workflowVersionService.getById(entity.getDraftVersionId());
        } else {
            newDraftVersion = true;
            draft = workflowVersionService.createDraftFromPublished(entity.getPublishedVersionId());
            entity.setDraftVersionId(draft.getId());
            workflowMapper.updateById(entity);
        }

        // 更新节点
        if (Objects.nonNull(param.getNodes())) {
            List<WorkflowNodeVO> nodes = param.getNodes();
            nodes.forEach(node -> {
                if (newDraftVersion) {
                    node.setId(null);
                }
                node.setWorkflowVersionId(draft.getId());
                node.setWorkflowId(id);
            });
            workflowNodeService.replaceByWorkflowVersionId(draft.getId(), nodes);
        }
        // 更新边
        if (Objects.nonNull(param.getEdges())) {
            List<WorkflowEdgeVO> edges = param.getEdges();
            edges.forEach(edge -> {
                if (newDraftVersion) {
                    edge.setId(null);
                }
                edge.setWorkflowVersionId(draft.getId());
                edge.setWorkflowId(id);
            });
            workflowEdgeService.replaceByWorkflowVersionId(draft.getId(), edges);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updatePublishById(String id) {
        WorkflowEntity entity = workflowMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        String draftVersionId = entity.getDraftVersionId();
        workflowVersionService.updatePublishById(draftVersionId);

        entity.setDraftVersionId("");
        entity.setPublishedVersionId(draftVersionId);
        entity.setWorkflowStatus(WorkflowStatusEnum.PUBLISHED);
        return workflowMapper.updateOneById(entity);
    }

    @Override
    public WorkflowVO getById(String id) {
        return WorkflowConverter.INSTANCE.toVO(workflowMapper.selectById(id));
    }


    @Override
    public WorkflowVO getById(String id, boolean latestReleased) {
        WorkflowVO workflow = WorkflowConverter.INSTANCE.toVO(workflowMapper.selectById(id));
        if (Objects.isNull(workflow)) {
            log.warn("data is not exist id:{}", id);
            return null;
        }
        String versionId = latestReleased ? workflow.getPublishedVersionId() :
                (StrUtil.isBlank(workflow.getDraftVersionId()) ? workflow.getPublishedVersionId() : workflow.getDraftVersionId());
        workflow.setLatestVersion(workflowVersionService.getWorkflowVersionById(versionId));
        return workflow;
    }

    @Override
    public WorkflowDetailVO getDetailById(String id, boolean latestReleased) {
        WorkflowEntity entity = workflowMapper.selectById(id);
        if (Objects.isNull(entity)) {
            log.warn("data is not exist id:{}", id);
            return null;
        }

        String versionId = latestReleased ? entity.getPublishedVersionId() :
                (StrUtil.isBlank(entity.getDraftVersionId()) ? entity.getPublishedVersionId() : entity.getDraftVersionId());
        return getDetailByIdAndVersionId(id, versionId);
    }

    @Override
    public WorkflowDetailVO getDetailByIdAndVersionId(String id, String versionId) {
        WorkflowDetailVO workflow = WorkflowConverter.INSTANCE.toDetailVO(workflowMapper.selectById(id));
        if (Objects.isNull(workflow)) {
            log.warn("data is not exist id:{}", id);
            return null;
        }
        workflow.setVersion(workflowVersionService.getWorkflowVersionById(versionId));
        workflow.setNodes(workflowNodeService.list(WorkflowNodeListParam.builder().workflowVersionId(versionId).build()));
        workflow.setEdges(workflowEdgeService.listByWorkflowVersionId(versionId));
        return workflow;
    }


    @Override
    public List<WorkflowVO> list(AppListParam param) {
        List<WorkflowEntity> entityList = workflowMapper.selectList(
                CommonWrappers.<WorkflowEntity>lambdaQuery()
//                        .likeIfPresent(AppEntity::getName, param.getName())
                        .orderByDesc(BaseEntity::getCreatedAt)
        );
        return assembleList(WorkflowConverter.INSTANCE.toListVO(entityList), false);
    }

    @Override
    public PageDTO<WorkflowListResponse> pageWorkflows(WorkflowPageRequest request) {
        Integer pageNo = Objects.isNull(request.getPageNo()) ? PageConstant.PAGE_NO : request.getPageNo();
        Integer pageSize = Objects.isNull(request.getPageSize()) ? PageConstant.PAGE_SIZE : request.getPageSize();

        IPage<WorkflowListResponse> page = workflowMapper.selectWorkflowPage(new Page<>((long) pageNo, (long) pageSize), request);

        return PageDTO.of(pageNo, pageSize, (int) page.getTotal(), page.getRecords());
    }

    private List<WorkflowVO> assembleList(List<WorkflowVO> records, boolean latestReleased) {
        if (CollUtil.isEmpty(records)) {
            return records;
        }
        List<String> appIds = records.stream().map(item ->
                        latestReleased ? item.getPublishedVersionId() : (StrUtil.isBlank(item.getDraftVersionId()) ? item.getPublishedVersionId() : item.getDraftVersionId()))
                .toList();

        Map<String, WorkflowVersionVO> versionMap = workflowVersionService.mapByIds(appIds);

        for (WorkflowVO vo : records) {
            if (versionMap.containsKey(vo.getDraftVersionId())) {
                vo.setLatestVersion(versionMap.get(vo.getDraftVersionId()));
            } else if (versionMap.containsKey(vo.getPublishedVersionId())) {
                vo.setLatestVersion(versionMap.get(vo.getPublishedVersionId()));
            }

        }
        return records;
    }

}
