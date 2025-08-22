package cn.hengzq.orange.ai.common.biz.workflow.event;

import org.springframework.context.ApplicationEvent;

/**
 * 工作流执行事件
 */
public class WorkflowRunEvent extends ApplicationEvent {

    public WorkflowRunEvent(String runId) {
        super(runId);
    }
}
