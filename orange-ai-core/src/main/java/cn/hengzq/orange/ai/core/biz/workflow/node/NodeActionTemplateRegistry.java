package cn.hengzq.orange.ai.core.biz.workflow.node;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowNodeTypeEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NodeActionTemplateRegistry {

    private final Map<WorkflowNodeTypeEnum, NodeActionTemplate> nodeActionTemplateMap;

    public NodeActionTemplateRegistry(List<NodeActionTemplate> templates) {
        this.nodeActionTemplateMap = new HashMap<>(templates.size());
        templates.forEach(template -> nodeActionTemplateMap.put(template.getNodeType(), template));
    }

    public NodeActionTemplate getNodeActionTemplate(WorkflowNodeTypeEnum nodeType) {
        return nodeActionTemplateMap.get(nodeType);
    }
}
