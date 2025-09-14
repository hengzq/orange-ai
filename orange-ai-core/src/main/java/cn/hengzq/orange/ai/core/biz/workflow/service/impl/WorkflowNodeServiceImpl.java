package cn.hengzq.orange.ai.core.biz.workflow.service.impl;

import cn.hengzq.orange.ai.common.biz.app.vo.param.WorkflowPageRequest;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.workflow.constant.ParamTypeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowConstant;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowNodeTypeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowStatusEnum;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowNodeVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowVersionVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.NodeInputConfig;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.NodeOutputConfig;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.Param;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.Position;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.*;
import cn.hengzq.orange.ai.core.biz.workflow.converter.WorkflowConverter;
import cn.hengzq.orange.ai.core.biz.workflow.converter.WorkflowNodeConverter;
import cn.hengzq.orange.ai.core.biz.workflow.converter.WorkflowVersionConverter;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowEntity;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowNodeEntity;
import cn.hengzq.orange.ai.core.biz.workflow.mapper.WorkflowMapper;
import cn.hengzq.orange.ai.core.biz.workflow.mapper.WorkflowNodeMapper;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowNodeService;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class WorkflowNodeServiceImpl implements WorkflowNodeService {

    private final WorkflowMapper workflowMapper;

    private final WorkflowVersionService workflowVersionService;

    private final WorkflowNodeMapper workflowNodeMapper;

    @Override
    public Boolean removeById(String id) {
        return workflowNodeMapper.deleteOneById(id);
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
    public WorkflowNodeVO getById(String id) {
        return WorkflowNodeConverter.INSTANCE.toVO(workflowNodeMapper.selectById(id));
    }


    @Override
    public WorkflowNodeVO create(CreateWorkflowNodeParam param) {
        WorkflowVersionVO draft = workflowVersionService.getOrCreateDraftByWorkflowId(param.getWorkflowId());
        WorkflowNodeEntity entity = WorkflowNodeConverter.INSTANCE.toEntity(draft.getId(), param);
        workflowNodeMapper.insertOne(entity);
        return WorkflowNodeConverter.INSTANCE.toVO(entity);
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
        start.setName(WorkflowNodeTypeEnum.START.getDescription());
        start.setNodeCode(WorkflowNodeTypeEnum.START.name());
        start.setWorkflowVersionId(workflowVersionId);
        start.setNodeType(WorkflowNodeTypeEnum.START);
        start.setPosition(new Position(200, 300));
        NodeInputConfig startInputConfig = new NodeInputConfig();
        Param param = Param.builder().name(WorkflowConstant.INPUT).type(ParamTypeEnum.STRING).build();
        List<Param> params = List.of(param);
        startInputConfig.setInputParams(params);
        start.setInputConfig(startInputConfig);
        NodeOutputConfig startOutputConfig = new NodeOutputConfig();
        startOutputConfig.setOutParams(params);
        start.setOutputConfig(startOutputConfig);

        WorkflowNodeEntity end = new WorkflowNodeEntity();
        end.setWorkflowId(workflowId);
        end.setName(WorkflowNodeTypeEnum.END.getDescription());
        end.setNodeCode(WorkflowNodeTypeEnum.END.name());
        end.setWorkflowVersionId(workflowVersionId);
        end.setNodeType(WorkflowNodeTypeEnum.END);
        end.setPosition(new Position(900, 300));
        Param endParam = Param.builder().name(WorkflowConstant.OUTPUT).type(ParamTypeEnum.STRING).build();
        NodeOutputConfig endOutputConfig = new NodeOutputConfig();
        endOutputConfig.setOutParams(List.of(endParam));
        start.setOutputConfig(endOutputConfig);
        workflowNodeMapper.insert(List.of(start, end));
    }

    @Override
    @Transactional
    public void replaceByWorkflowVersionId(String workflowVersionId, List<WorkflowNodeVO> nodes) {
        if (StrUtil.isBlank(workflowVersionId)) {
            log.error("Workflow version id is blank, cannot replace nodes.");
            return;
        }
        workflowNodeMapper.deleteByWorkflowVersionId(workflowVersionId);

        if (CollUtil.isEmpty(nodes)) {
            log.warn("No nodes to replace for workflow version id: {}", workflowVersionId);
            return;
        }
        nodes.forEach(node -> {
            node.setWorkflowVersionId(workflowVersionId);
        });
        List<WorkflowNodeEntity> entityList = WorkflowNodeConverter.INSTANCE.toListEntity(nodes);
        if (CollUtil.isEmpty(entityList)) {
            log.warn("No nodes to insert for workflow version id: {}", workflowVersionId);
            return;
        }
        workflowNodeMapper.insert(entityList);
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
