package cn.hengzq.orange.ai.core.biz.workflow.service.impl;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowConstant;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowNodeTypeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowRunStatusEnum;
import cn.hengzq.orange.ai.common.biz.workflow.vo.*;
import cn.hengzq.orange.ai.common.biz.workflow.vo.config.LlmNodeParameter;
import cn.hengzq.orange.ai.common.biz.workflow.vo.config.NodeInputConfig;
import cn.hengzq.orange.ai.common.biz.workflow.vo.config.NodeOutputConfig;
import cn.hengzq.orange.ai.common.biz.workflow.vo.config.Param;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.CreateWorkflowRunNodeParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.UpdateWorkflowRunNodeParam;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.util.PlaceholderUtils;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowRunEntity;
import cn.hengzq.orange.ai.core.biz.workflow.node.EnhancedNodeAction;
import cn.hengzq.orange.ai.core.biz.workflow.node.NodeActionTemplate;
import cn.hengzq.orange.ai.core.biz.workflow.node.NodeActionTemplateRegistry;
import cn.hengzq.orange.ai.core.biz.workflow.node.end.EndNode;
import cn.hengzq.orange.ai.core.biz.workflow.node.start.StartNode;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowNodeService;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowRunNodeService;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowService;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.ai.graph.*;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import com.alibaba.cloud.ai.graph.node.LlmNode;
import com.alibaba.cloud.ai.graph.state.strategy.AppendStrategy;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;
import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.alibaba.cloud.ai.graph.StateGraph.END;
import static com.alibaba.cloud.ai.graph.StateGraph.START;
import static com.alibaba.cloud.ai.graph.action.AsyncNodeAction.node_async;

@Slf4j
@Service
@AllArgsConstructor
public class WorkflowGraphBuilder {

    private final WorkflowService workflowService;

    private final WorkflowNodeService workflowNodeService;

    private final ChatModelServiceFactory chatModelServiceFactory;

    private final ModelService modelService;

    private final WorkflowRunNodeService workflowRunNodeService;
    private final WebSocketServletAutoConfiguration webSocketServletAutoConfiguration;

    private final NodeActionTemplateRegistry nodeActionTemplateRegistry;


    public CompiledGraph buildGraph(String workflowId) {
        try {
            WorkflowDetailVO workflow = workflowService.getDetailById(workflowId, false);
            WorkflowVersionVO version = workflow.getVersion();

            List<WorkflowNodeVO> nodes = workflow.getNodes();
            WorkflowNodeVO start = nodes.stream().filter(item -> item.getNodeType().equals(WorkflowNodeTypeEnum.START)).findFirst().orElse(null);
            WorkflowNodeVO end = nodes.stream().filter(item -> item.getNodeType().equals(WorkflowNodeTypeEnum.END)).findFirst().orElse(null);

            List<WorkflowEdgeVO> edges = workflow.getEdges();
            Map<String, List<WorkflowEdgeVO>> sourceEdgeMap = edges.stream().collect(Collectors.groupingBy(WorkflowEdgeVO::getSourceNodeId));

            OverAllStateFactory stateFactory = () -> {
                Map<String, Object> data = new HashMap<>();
                data.put("userPromptKey", "你是谁");

                OverAllState state = new OverAllState(data);
                state.registerKeyAndStrategy("userPromptKey", new AppendStrategy());
                state.registerKeyAndStrategy("messages", new AppendStrategy());
                state.registerKeyAndStrategy("outputKey", new AppendStrategy());
                return state;
            };

            StateGraph graph = new StateGraph(version.getName(), stateFactory);

            for (WorkflowNodeVO node : nodes) {
                if (Objects.isNull(node) || node.getNodeType().equals(WorkflowNodeTypeEnum.START) || node.getNodeType().equals(WorkflowNodeTypeEnum.END)) {
                    continue;
                }
                if (node.getNodeType().equals(WorkflowNodeTypeEnum.LLM)) {
                    ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(PlatformEnum.ALI_BAI_LIAN);
                    ChatModel chatModel = chatModelService.getOrCreateChatModel("qwen3-8b", "", "muovb3q5XyvX9l2yQN2xhtjAQ66gf65JK+bK6AT8H1lU6PJj31sU9g==");

                    ChatClient chatClient = ChatClient.builder(chatModel).build();
                    LlmNode llmNode = LlmNode.builder().chatClient(chatClient).userPromptTemplateKey("userPromptKey").messagesKey("messages").outputKey("outputKey").build();

                    graph.addNode(node.getNodeCode(), node_async(llmNode));
                }
            }

            for (WorkflowEdgeVO edge : edges) {
                if (edge.getSourceNodeId().equals(start.getNodeCode())) {
                    graph.addEdge(START, edge.getTargetNodeId());
                    continue;
                }
                if (edge.getTargetNodeId().equals(end.getNodeCode())) {
                    graph.addEdge(edge.getSourceNodeId(), END);
                    continue;
                }
                graph.addEdge(edge.getSourceNodeId(), edge.getTargetNodeId());
            }
            return graph.compile();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    public NodeAction buildNodeAction(WorkflowNodeVO node) {
        if (Objects.isNull(node)) {
            return null;
        }
        if (WorkflowNodeTypeEnum.START.equals(node.getNodeType())) {
            return new StartNode();
        } else if (WorkflowNodeTypeEnum.END.equals(node.getNodeType())) {
            return new EndNode();
        } else if (WorkflowNodeTypeEnum.LLM.equals(node.getNodeType())) {
            NodeInputConfig inputConfig = node.getInputConfig();
            LlmNodeParameter llmParam = inputConfig.getLlmParam();
            ModelVO model = modelService.getById(llmParam.getModelId());

            ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(model.getPlatform());
            ChatModel chatModel = chatModelService.getOrCreateChatModel(model.getModelName(), model.getBaseUrl(), model.getApiKey());
            ChatClient chatClient = ChatClient.builder(chatModel).build();
            return LlmNode.builder().chatClient(chatClient)
                    .userPromptTemplateKey(node.getNodeCode() + "." + WorkflowConstant.PROMPT)
                    .systemPromptTemplateKey(node.getNodeCode() + "." + WorkflowConstant.SYSTEM_PROMPT)
                    .outputKey(getStateParamKey(node.getNodeCode(), WorkflowConstant.OUTPUT))
                    .build();
        }

        return null;
    }


    public OverAllState buildOverAllState(WorkflowNodeVO node, Map<String, Object> input) {
        Map<String, Object> data = CollUtil.isNotEmpty(input) ? new HashMap<>(input) : new HashMap<>();

        if (WorkflowNodeTypeEnum.LLM.equals(node.getNodeType())) {
            NodeInputConfig inputConfig = node.getInputConfig();
            LlmNodeParameter llmParam = inputConfig.getLlmParam();

            data.put(WorkflowConstant.PROMPT, llmParam.getPrompt());
            data.put(WorkflowConstant.SYSTEM_PROMPT, llmParam.getSystemPrompt());
        }

        OverAllState state = new OverAllState(data);
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            state.registerKeyAndStrategy(entry.getKey(), new ReplaceStrategy());

        }
        return state;
    }

    public CompiledGraph buildGraph(WorkflowRunEntity runEntity) {
        if (Objects.isNull(runEntity)) {
            log.error("runEntity is null");
            return null;
        }

        try {
            WorkflowDetailVO workflow = workflowService.getDetailByIdAndVersionId(runEntity.getWorkflowId(), runEntity.getWorkflowVersionId());
            WorkflowVersionVO version = workflow.getVersion();

            List<WorkflowNodeVO> nodes = workflow.getNodes();
            WorkflowNodeVO start = nodes.stream().filter(item -> item.getNodeType().equals(WorkflowNodeTypeEnum.START)).findFirst().orElse(null);
            WorkflowNodeVO end = nodes.stream().filter(item -> item.getNodeType().equals(WorkflowNodeTypeEnum.END)).findFirst().orElse(null);

            List<WorkflowEdgeVO> edges = workflow.getEdges();

            StateGraph graph = new StateGraph(version.getName(), () -> new HashMap<>());

            for (WorkflowNodeVO node : nodes) {
                if (Objects.isNull(node)) {
                    continue;
                }
                graph.addNode(node.getNodeCode(), node_async(buildEnhancedNodeAction(runEntity, node)));
            }

            graph.addEdge(START, start.getNodeCode());
            graph.addEdge(end.getNodeCode(), END);
            for (WorkflowEdgeVO edge : edges) {
                if (edge.getSourceNodeId().equals(start.getNodeCode())) {
                    graph.addEdge(start.getNodeCode(), edge.getTargetNodeId());
                    continue;
                }
                if (edge.getTargetNodeId().equals(end.getNodeCode())) {
                    graph.addEdge(edge.getSourceNodeId(), end.getNodeCode());
                    continue;
                }
                graph.addEdge(edge.getSourceNodeId(), edge.getTargetNodeId());
            }
            return graph.compile();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;


    }

    private NodeAction buildEnhancedNodeAction(WorkflowRunEntity runEntity, WorkflowNodeVO node) {
        NodeActionTemplate nodeActionTemplate = nodeActionTemplateRegistry.getNodeActionTemplate(node.getNodeType());
        NodeAction nodeAction = nodeActionTemplate.build(node);

        AtomicReference<WorkflowRunNodeVO> runNodeRef = new AtomicReference<>();
        return EnhancedNodeAction.builder()
                .before(state -> {
                    log.info("before node, runId:{}", runEntity.getId());
                    Map<String, Object> inputData = nodeActionTemplate.beforeExecute(state, runEntity, node);

                    CreateWorkflowRunNodeParam createWorkflowRunNodeParam = CreateWorkflowRunNodeParam.builder()
                            .runId(runEntity.getId())
                            .nodeId(node.getId())
                            .inputData(new JSONObject(inputData))
                            .runStatus(WorkflowRunStatusEnum.RUNNING)
                            .build();

                    runNodeRef.set(workflowRunNodeService.create(createWorkflowRunNodeParam));
                })
                .delegate(nodeAction)
                .after((state, result) -> {
                    log.info("after: result: {}", result);
                    WorkflowRunNodeVO runNodeVO = runNodeRef.get();
                    if (Objects.isNull(runNodeVO)) {
                        log.warn("runNodeVO is null");
                        return;
                    }
                    Map<String, Object> outputData = nodeActionTemplate.afterExecute(state, node, result);

                    workflowRunNodeService.updateById(runNodeVO.getId(), UpdateWorkflowRunNodeParam.builder()
                            .runStatus(WorkflowRunStatusEnum.SUCCEEDED)
                            .outputData(new JSONObject(outputData))
                            .build());
                }).onError((state, e) -> {
                    log.error("buildEnhancedNodeAction error", e);
                    WorkflowRunNodeVO runNodeVO = runNodeRef.get();
                    workflowRunNodeService.updateById(runNodeVO.getId(), UpdateWorkflowRunNodeParam.builder()
                            .runStatus(WorkflowRunStatusEnum.FAILED)
                            .build());
                })
                .build();
    }


    private static @NotNull String getStateParamKey(String nodeCode, String param) {
        return nodeCode + "." + param;
    }


}
