package cn.hengzq.orange.ai.core.biz.workflow.listener;

import cn.hengzq.orange.ai.common.biz.workflow.event.WorkflowRunEvent;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowRunService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 工作流监听器
 */
@Slf4j
@Component
@AllArgsConstructor
public class WorkflowRunListener {

    private final WorkflowRunService workflowRunService;

    /**
     * 切片向量化
     */
    @Async()
    @EventListener(WorkflowRunEvent.class)
    public void add(WorkflowRunEvent event) {
        try {
            if (event.getSource() instanceof String runId) {
                workflowRunService.rerun(runId);
            } else {
                log.warn("params type error.param:{}", event.getSource());
            }
        } catch (Exception e) {
            log.error("workflow run error. runId{}", event.getSource(), e);
        }
    }

}
