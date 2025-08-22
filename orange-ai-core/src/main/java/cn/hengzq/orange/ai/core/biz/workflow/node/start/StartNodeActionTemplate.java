package cn.hengzq.orange.ai.core.biz.workflow.node.start;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowConstant;
import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowNodeTypeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowNodeVO;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowRunEntity;
import cn.hengzq.orange.ai.core.biz.workflow.node.NodeActionTemplate;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class StartNodeActionTemplate implements NodeActionTemplate {

    @Override
    public WorkflowNodeTypeEnum getNodeType() {
        return WorkflowNodeTypeEnum.START;
    }

    @Override
    public NodeAction build(WorkflowNodeVO node) {
        return new StartNode();
    }

    @Override
    public Map<String, Object> beforeExecute(OverAllState state, WorkflowRunEntity workflowRun, WorkflowNodeVO node) {
        Map<String, Object> inputData = new HashMap<>();
        Map<String, Object> stateInputData = new HashMap<>();
        stateInputData.put(WorkflowConstant.CURRENT_NODE, node);

        if (Objects.nonNull(workflowRun.getInputData())) {
            workflowRun.getInputData().forEach((key, value) -> inputData.put(getStateParamKey(node.getNodeCode(), key), value));
        }

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
        return result;
    }
}
