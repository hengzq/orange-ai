package cn.hengzq.orange.ai.core.biz.workflow.node.llm;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.model.dto.ModelResponse;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowConstant;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowNodeTypeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowNodeVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.LlmNodeParameter;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.NodeInputConfig;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.NodeOutputConfig;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.Param;
import cn.hengzq.orange.ai.common.util.PlaceholderUtils;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowRunEntity;
import cn.hengzq.orange.ai.core.biz.workflow.node.NodeActionTemplate;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import com.alibaba.cloud.ai.graph.node.LlmNode;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class LlmNodeActionTemplate implements NodeActionTemplate {

    private final ModelService modelService;

    private final ChatModelServiceFactory chatModelServiceFactory;


    @Override
    public WorkflowNodeTypeEnum getNodeType() {
        return WorkflowNodeTypeEnum.LLM;
    }

    @Override
    public NodeAction build(WorkflowNodeVO node) {

        NodeInputConfig inputConfig = node.getInputConfig();
        LlmNodeParameter llmParam = inputConfig.getLlmParam();
        ModelResponse model = modelService.getById(llmParam.getModelId());

        ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(model.getPlatform());
        ChatModel chatModel = chatModelService.getOrCreateChatModel(model.getModelName(), model.getBaseUrl(), model.getApiKey());
        ChatClient chatClient = ChatClient.builder(chatModel).build();
        return LlmNode.builder().chatClient(chatClient)
                .userPromptTemplateKey(node.getNodeCode() + "." + WorkflowConstant.PROMPT)
                .systemPromptTemplateKey(node.getNodeCode() + "." + WorkflowConstant.SYSTEM_PROMPT)
                .build();
    }

    @Override
    public Map<String, Object> beforeExecute(OverAllState state, WorkflowRunEntity workflowRun, WorkflowNodeVO node) {
        Map<String, Object> inputData = new HashMap<>();
        Map<String, Object> stateInputData = new HashMap<>();
        stateInputData.put(WorkflowConstant.CURRENT_NODE, node);

        NodeInputConfig inputConfig = node.getInputConfig();
        List<Param> inputParams = inputConfig.getInputParams();
        Map<String, Object> tempInputData = new HashMap<>();
        if (Objects.nonNull(inputParams)) {
            for (Param param : inputParams) {
                String value = PlaceholderUtils.replacePlaceholders(param.getValue(), state.data());
                tempInputData.put(param.getName(), value);
                inputData.put(getStateParamKey(node.getNodeCode(), param.getName()), value);
            }
        }
        LlmNodeParameter llmParam = inputConfig.getLlmParam();
        stateInputData.put(getStateParamKey(node.getNodeCode(), WorkflowConstant.SYSTEM_PROMPT), PlaceholderUtils.replacePlaceholders(llmParam.getSystemPrompt(), tempInputData));
        stateInputData.put(getStateParamKey(node.getNodeCode(), WorkflowConstant.PROMPT), PlaceholderUtils.replacePlaceholders(llmParam.getPrompt(), tempInputData));

        if (CollUtil.isNotEmpty(inputData)) {
            stateInputData.putAll(inputData);
        }
        if (CollUtil.isNotEmpty(stateInputData)) {
            for (String key : stateInputData.keySet()) {
                state.registerKeyAndStrategy(key, new ReplaceStrategy());
            }
            state.input(stateInputData);
        }
        return inputData;
    }

    @Override
    public Map<String, Object> afterExecute(OverAllState state, WorkflowNodeVO node, Map<String, Object> result) {
        Map<String, Object> outputData = new HashMap<>();

        NodeOutputConfig outputConfig = node.getOutputConfig();
        List<Param> outParams = outputConfig.getOutParams();

        if (result.get("messages") instanceof AssistantMessage message) {
            for (Param param : outParams) {
                outputData.put(getStateParamKey(node.getNodeCode(), param.getName()), message.getText());
            }
        }

        if (CollUtil.isNotEmpty(outputData)) {
            for (String key : outputData.keySet()) {
                state.registerKeyAndStrategy(key, new ReplaceStrategy());
            }
            state.input(outputData);
        }
        return outputData;
    }
}
