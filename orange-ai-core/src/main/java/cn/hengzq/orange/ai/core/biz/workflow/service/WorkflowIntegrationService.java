package cn.hengzq.orange.ai.core.biz.workflow.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;

@Slf4j
public class WorkflowIntegrationService {

    private final WorkflowRunService workflowRunService;

    public WorkflowIntegrationService(WorkflowRunService workflowRunService) {
        this.workflowRunService = workflowRunService;
    }

    @Tool(description = "Invoke a workflow by its ID")
    String invokeWorkflow(String workflowId) {
        log.error("Invoking workflow with ID: {}", workflowId);
        return workflowRunService.executeWorkflow();

    }
}
