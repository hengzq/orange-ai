package cn.hengzq.orange.ai.core.biz.workflow.service.impl;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowRunScopeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowRunStatusEnum;
import cn.hengzq.orange.ai.common.biz.workflow.event.WorkflowRunEvent;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowNodeVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowRunDetailVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowRunVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowCreateRequest;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.CreateWorkflowRunParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowRunParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.result.WorkflowRunResult;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.workflow.converter.WorkflowRunConverter;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowNodeRunEntity;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowRunEntity;
import cn.hengzq.orange.ai.core.biz.workflow.mapper.WorkflowNodeRunMapper;
import cn.hengzq.orange.ai.core.biz.workflow.mapper.WorkflowRunMapper;
import cn.hengzq.orange.ai.core.biz.workflow.node.EnhancedNodeAction;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowNodeService;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowRunNodeService;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowRunService;
import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import cn.hengzq.orange.common.util.Assert;
import com.alibaba.cloud.ai.graph.CompiledGraph;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import com.alibaba.cloud.ai.graph.node.LlmNode;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class WorkflowRunServiceImpl implements WorkflowRunService {

    private final ChatModelServiceFactory chatModelServiceFactory;

    private final WorkflowGraphBuilder workflowGraphBuilder;

    private final WorkflowRunMapper workflowRunMapper;

    private final WorkflowNodeRunMapper workflowNodeRunMapper;

    private final WorkflowNodeService workflowNodeService;

    private final WorkflowRunNodeService workflowRunNodeService;

    private final ApplicationContext applicationContext;


    @Override
    public WorkflowRunVO create(CreateWorkflowRunParam param) {
        WorkflowRunEntity entity = WorkflowRunConverter.INSTANCE.toEntity(param);
        workflowRunMapper.insertOne(entity);
        return WorkflowRunConverter.INSTANCE.toVO(entity);
    }

    @Override
    public void update(WorkflowNodeRunEntity entity) {
        workflowNodeRunMapper.updateById(entity);
    }

    @Override
    public void rerun(String id) {
        WorkflowRunEntity entity = workflowRunMapper.selectById(id);
        if (Objects.isNull(entity)) {
            log.warn("entity is null, runId:{}", id);
            return;
        }

        CompiledGraph graph = workflowGraphBuilder.buildGraph(entity);

        Optional<OverAllState> invoke = null;
        try {
            invoke = graph.invoke(Map.of());
        } catch (GraphRunnerException e) {
            throw new RuntimeException(e);
        }
        OverAllState state = invoke.orElse(null);
        if (Objects.isNull(state)) {
            log.warn("state is null, runId:{}", id);
        }

        entity.setRunStatus(WorkflowRunStatusEnum.SUCCEEDED);
        entity.setOutputData(WorkflowRunConverter.INSTANCE.toJson(state));
        workflowRunMapper.updateById(entity);
    }

    @Override
    public String executeWorkflow() {
        log.error("workflow execution start...");
        CompiledGraph graph = workflowGraphBuilder.buildGraph("1953434943741345792");
        Optional<OverAllState> invoke = null;
        try {
            invoke = graph.invoke(Map.of("messages", List.of(new UserMessage("你好，你是谁"))));
        } catch (GraphRunnerException e) {
            throw new RuntimeException(e);
        }
        log.warn("invoke result: %s%n", invoke);
        return "我是工作流返回的结果";

    }


    @Override
    public String invokeLlmNode(WorkflowCreateRequest param) {
        ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(PlatformEnum.ALI_BAI_LIAN);
        ChatModel chatModel = chatModelService.getOrCreateChatModel("qwen3-8b", "", "muovb3q5XyvX9l2yQN2xhtjAQ66gf65JK+bK6AT8H1lU6PJj31sU9g==");

        ChatClient chatClient = ChatClient.builder(chatModel).build();
        LlmNode llmNode = LlmNode.builder().chatClient(chatClient).userPromptTemplateKey("userPromptKey").messagesKey("messages").build();
        Map<String, Object> data = new HashMap<>();
        data.put("userPromptKey", "你是谁");

        OverAllState state = new OverAllState(data);
        state.registerKeyAndStrategy("userPromptKey", new ReplaceStrategy());
        Map<String, Object> apply = null;
        try {
            apply = llmNode.apply(state);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return JSON.toJSONString(apply);
    }

    @Override
    public WorkflowRunResult runWorkflowById(String id, WorkflowRunParam param) {
        CompiledGraph graph = workflowGraphBuilder.buildGraph(id);
        Optional<OverAllState> invoke = null;
        try {
            invoke = graph.invoke(Map.of("messages", List.of(new UserMessage("你好，你是谁"))));
        } catch (GraphRunnerException e) {
            throw new RuntimeException(e);
        }
        log.warn("invoke result: {}", invoke);

        return null;
    }

    @Override
    public WorkflowRunResult runWorkflowAsync(String workflowId, WorkflowRunParam param) {
        WorkflowRunEntity entity = new WorkflowRunEntity();
        entity.setRunScope(WorkflowRunScopeEnum.WORKFLOW);
        entity.setWorkflowId(workflowId);
        entity.setWorkflowVersionId(param.getWorkflowVersionId());
        entity.setInputData(new JSONObject(param.getInput()));
        entity.setRunStatus(WorkflowRunStatusEnum.PENDING);
        workflowRunMapper.insert(entity);

        applicationContext.publishEvent(new WorkflowRunEvent(entity.getId()));
        return WorkflowRunConverter.INSTANCE.toResult(entity);
    }

    @Override
    public WorkflowRunVO runSingleNodeAsync(String workflowId, String nodeId, WorkflowRunParam param) {
        WorkflowRunVO runVO = create(CreateWorkflowRunParam.builder()
                .workflowId(workflowId)
                .workflowVersionId(param.getWorkflowVersionId())
                .runScope(WorkflowRunScopeEnum.NODE)
                .build());

        WorkflowNodeRunEntity entity = new WorkflowNodeRunEntity();
        entity.setRunId(runVO.getId());
        entity.setNodeId(nodeId);
        workflowNodeRunMapper.insert(entity);

        applicationContext.publishEvent(new WorkflowRunEvent(runVO.getId()));
        return runVO;
    }

    @Override
    public WorkflowRunVO runSingleNodeSync(String workflowId, String nodeId, WorkflowRunParam param) {
        WorkflowRunVO runVO = create(CreateWorkflowRunParam.builder()
                .workflowId(workflowId)
                .workflowVersionId(param.getWorkflowVersionId())
                .runScope(WorkflowRunScopeEnum.NODE)
                .build());

        WorkflowNodeVO nodeVO = workflowNodeService.getById(nodeId);
        Assert.nonNull(nodeVO, GlobalErrorCodeConstant.GLOBAL_DATA_NOT_EXIST);

        WorkflowNodeRunEntity entity = new WorkflowNodeRunEntity();
        entity.setRunId(runVO.getId());
        entity.setNodeId(nodeVO.getId());
        entity.setInputData(new JSONObject(param.getInput()));
        entity.setRunStatus(WorkflowRunStatusEnum.PENDING);
        workflowNodeRunMapper.insert(entity);

        EnhancedNodeAction nodeAction = this.buildEnhancedNodeAction(entity, nodeVO);
        OverAllState state = workflowGraphBuilder.buildOverAllState(nodeVO, param.getInput());

        Map<String, Object> result = nodeAction.apply(state);
        return runVO;
    }

    @Override
    public WorkflowRunDetailVO getDetailById(String id) {
        WorkflowRunEntity entity = workflowRunMapper.selectById(id);
        WorkflowRunDetailVO vo = WorkflowRunConverter.INSTANCE.toDetailVO(entity);
        if (Objects.isNull(vo)) {
            return null;
        }
        vo.setNodes(workflowRunNodeService.listByRunId(id));
        return vo;
    }

    private EnhancedNodeAction buildEnhancedNodeAction(WorkflowNodeRunEntity entity, WorkflowNodeVO nodeVO) {
        if (Objects.isNull(entity)) {
            return null;
        }
        NodeAction nodeAction = workflowGraphBuilder.buildNodeAction(nodeVO);
        return new EnhancedNodeAction(
                "",
                nodeAction,
                // 前置：打印开始
                state -> {
                    log.info("before state:{}", state);

                    Map<String, Object> stateMap = new HashMap<>();
                    stateMap.put("data", state.data());
                    stateMap.put("keyStrategies", state.keyStrategies());
                    entity.setRunContext(new JSONObject(stateMap));
                    entity.setRunStatus(WorkflowRunStatusEnum.RUNNING);
                    this.update(entity);
                },
                // 后置：打印结果
                (state, result) -> {
                    log.info("after: result: {}", result);
                    entity.setOutputData(new JSONObject(result));
                    entity.setRunStatus(WorkflowRunStatusEnum.SUCCEEDED);
                    this.update(entity);
                },
                // 异常处理
                (state, ex) -> {
                    log.error("error msg:{}", ex.getMessage());
                    entity.setRunStatus(WorkflowRunStatusEnum.FAILED);
                    this.update(entity);
                }
        );
    }


}
