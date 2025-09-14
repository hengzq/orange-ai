package cn.hengzq.orange.ai.core.biz.workflow.node.end;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowNodeTypeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowNodeVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.NodeOutputConfig;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.Param;
import cn.hengzq.orange.ai.common.util.PlaceholderUtils;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowRunEntity;
import cn.hengzq.orange.ai.core.biz.workflow.node.NodeActionTemplate;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EndNodeActionTemplate implements NodeActionTemplate {

    @Override
    public WorkflowNodeTypeEnum getNodeType() {
        return WorkflowNodeTypeEnum.END;
    }

    @Override
    public NodeAction build(WorkflowNodeVO node) {
        return new EndNode();
    }

    @Override
    public Map<String, Object> beforeExecute(OverAllState state, WorkflowRunEntity workflowRun, WorkflowNodeVO node) {
        return Map.of();
    }

    @Override
    public Map<String, Object> afterExecute(OverAllState state, WorkflowNodeVO node, Map<String, Object> result) {
        Map<String, Object> outputData = new HashMap<>();
        NodeOutputConfig outputConfig = node.getOutputConfig();

        List<Param> outParams = outputConfig.getOutParams();
        for (Param param : outParams) {
            outputData.put(getStateParamKey(node.getNodeCode(), param.getName()), PlaceholderUtils.replacePlaceholders(param.getValue(), state.data()));
        }

        return outputData;
    }
}
