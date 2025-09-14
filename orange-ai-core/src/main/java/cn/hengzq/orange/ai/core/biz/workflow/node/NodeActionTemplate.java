package cn.hengzq.orange.ai.core.biz.workflow.node;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowNodeTypeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowNodeVO;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowRunEntity;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;

import java.util.Map;

public interface NodeActionTemplate {

    WorkflowNodeTypeEnum getNodeType();

    NodeAction build(WorkflowNodeVO node);

    /**
     * 在节点动作执行之前
     *
     * @return 输入数据
     */
    Map<String, Object> beforeExecute(OverAllState state, WorkflowRunEntity workflowRun, WorkflowNodeVO node);

    Map<String, Object> afterExecute(OverAllState state, WorkflowNodeVO node, Map<String, Object> result);


    default String getStateParamKey(String nodeCode, String param) {
        return nodeCode + "." + param;
    }

}
