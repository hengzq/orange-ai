package cn.hengzq.orange.ai.core.biz.workflow.service.impl;

import cn.hengzq.orange.ai.common.biz.app.vo.param.WorkflowPageRequest;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowNodeTypeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowStatusEnum;
import cn.hengzq.orange.ai.common.biz.workflow.dto.*;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.Position;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.*;
import cn.hengzq.orange.ai.core.biz.workflow.converter.WorkflowConverter;
import cn.hengzq.orange.ai.core.biz.workflow.converter.WorkflowEdgeConverter;
import cn.hengzq.orange.ai.core.biz.workflow.converter.WorkflowNodeConverter;
import cn.hengzq.orange.ai.core.biz.workflow.converter.WorkflowVersionConverter;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowEdgeEntity;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowEntity;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowNodeEntity;
import cn.hengzq.orange.ai.core.biz.workflow.mapper.WorkflowEdgeMapper;
import cn.hengzq.orange.ai.core.biz.workflow.mapper.WorkflowMapper;
import cn.hengzq.orange.ai.core.biz.workflow.mapper.WorkflowNodeMapper;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowEdgeService;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowVersionService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.mybatis.entity.BaseEntity;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class WorkflowEdgeServiceImpl implements WorkflowEdgeService {

    private final WorkflowMapper workflowMapper;

    private final WorkflowVersionService workflowVersionService;

    private final WorkflowNodeMapper workflowNodeMapper;

    private final WorkflowEdgeMapper workflowEdgeMapper;

    @Override
    public Boolean removeById(String id) {
        return workflowEdgeMapper.deleteOneById(id);
    }

    @Override
    @Transactional
    public String add(WorkflowCreateRequest param) {
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
//            draftVersionId = workflowVersionService.createDraftFromPublishedVersionId(entity.getPublishedVersionId());
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
    public WorkflowEdgeVO create(CreateWorkflowEdgeParam param) {
        WorkflowVersionVO draft = workflowVersionService.getOrCreateDraftByWorkflowId(param.getWorkflowId());
        WorkflowEdgeEntity entity = WorkflowEdgeConverter.INSTANCE.toEntity(draft.getId(), param);
        workflowEdgeMapper.insertOne(entity);
        return WorkflowEdgeConverter.INSTANCE.toVO(entity);
    }

    @Override
    public List<WorkflowNodeVO> list(WorkflowNodeListParam param) {
        List<WorkflowNodeEntity> entityList = workflowNodeMapper.selectList(
                CommonWrappers.<WorkflowNodeEntity>lambdaQuery()
                        .eqIfPresent(WorkflowNodeEntity::getWorkflowVersionId, param.getWorkflowVersionId())
                        .orderByDesc(BaseEntity::getCreatedAt)
        );
        return WorkflowNodeConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public void createStartAndEndNodes(String workflowId, String workflowVersionId) {
        WorkflowNodeEntity start = new WorkflowNodeEntity();
        start.setWorkflowId(workflowId);
        start.setWorkflowVersionId(workflowVersionId);
        start.setNodeType(WorkflowNodeTypeEnum.START);
        start.setPosition(new Position(200, 300));

        WorkflowNodeEntity end = new WorkflowNodeEntity();
        end.setWorkflowId(workflowId);
        end.setWorkflowVersionId(workflowVersionId);
        end.setNodeType(WorkflowNodeTypeEnum.END);
        end.setPosition(new Position(700, 300));
        workflowNodeMapper.insert(List.of(start, end));
    }

    @Override
    public List<WorkflowEdgeVO> listByWorkflowVersionId(String workflowVersionId) {
        if (StrUtil.isBlank(workflowVersionId)) {
            return Collections.emptyList();
        }
        List<WorkflowEdgeEntity> entityList = workflowEdgeMapper.selectList(
                CommonWrappers.<WorkflowEdgeEntity>lambdaQuery()
                        .eq(WorkflowEdgeEntity::getWorkflowVersionId, workflowVersionId)
                        .orderByDesc(BaseEntity::getCreatedAt)
        );
        return CollUtil.isEmpty(entityList) ? Collections.emptyList() : WorkflowEdgeConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public void replaceByWorkflowVersionId(String workflowVersionId, List<WorkflowEdgeVO> edges) {
        if (StrUtil.isBlank(workflowVersionId)) {
            log.error("Workflow version id is blank, cannot replace nodes.");
            return;
        }
        workflowEdgeMapper.deleteByWorkflowVersionId(workflowVersionId);

        if (CollUtil.isEmpty(edges)) {
            log.warn("Workflow edge is empty, cannot replace nodes.");
            return;
        }
        edges.forEach(edge -> {
            edge.setWorkflowVersionId(workflowVersionId);
        });
        List<WorkflowEdgeEntity> entityList = WorkflowEdgeConverter.INSTANCE.toListEntity(edges);
        if (CollUtil.isEmpty(entityList)) {
            log.warn("Workflow edge is empty, cannot replace nodes. ");
            return;
        }
        workflowEdgeMapper.insert(entityList);
    }

    @Override
    public PageDTO<WorkflowVO> page(WorkflowPageRequest param) {
        PageDTO<WorkflowEntity> page = workflowMapper.selectPage(param, CommonWrappers.<WorkflowEntity>lambdaQuery()
                .orderByDesc(BaseEntity::getCreatedAt)
        );
        if (Objects.isNull(page) || CollUtil.isEmpty(page.getRecords())) {
            return PageDTO.of(param.getPageNo(), param.getPageSize());
        }

        PageDTO<WorkflowVO> pageResult = WorkflowConverter.INSTANCE.toPage(page);
        if (CollUtil.isEmpty(pageResult.getRecords())) {
            return pageResult;
        }
        pageResult.setRecords(assembleList(pageResult.getRecords(), false));
        return pageResult;
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
