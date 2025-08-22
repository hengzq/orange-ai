package cn.hengzq.orange.ai.core.biz.workflow.node.end;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;

import java.util.Map;

public class EndNode implements NodeAction {

    @Override
    public Map<String, Object> apply(OverAllState t) throws Exception {
        return Map.of();
    }
}
