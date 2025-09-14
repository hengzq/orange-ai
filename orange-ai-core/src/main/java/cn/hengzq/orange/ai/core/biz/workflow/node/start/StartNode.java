package cn.hengzq.orange.ai.core.biz.workflow.node.start;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowConstant;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowNodeVO;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class StartNode implements NodeAction {

    @Override
    public Map<String, Object> apply(OverAllState state) throws Exception {
        WorkflowNodeVO node = (WorkflowNodeVO) state.value(WorkflowConstant.CURRENT_NODE).orElse(null);
        if (node == null) {
            log.warn("current node is null");
            return null;
        }
        return state.data().entrySet().stream()
                .filter(entry -> entry.getKey().contains(node.getNodeCode()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
