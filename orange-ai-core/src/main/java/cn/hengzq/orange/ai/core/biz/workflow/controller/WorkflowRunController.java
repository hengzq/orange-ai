package cn.hengzq.orange.ai.core.biz.workflow.controller;


import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowRunDetailVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowRunVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowRunParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.result.WorkflowRunResult;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowRunService;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author hengzq
 */
@Tag(name = "AI - 工作流 - 执行", description = "用于触发工作流中的节点执行，如大模型节点")
@RestController
@RequiredArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/workflow-runs")
public class WorkflowRunController {

    private final WorkflowRunService workflowRunService;

    @Operation(summary = "异步执行工作流", operationId = "orange-ai:workflows:add")
    @PostMapping("/{workflowId}/run")
    public Result<WorkflowRunResult> runWorkflowAsync(@PathVariable String workflowId, @RequestBody @Validated WorkflowRunParam param) {
        return ResultWrapper.ok(workflowRunService.runWorkflowAsync(workflowId, param));
    }


    @Operation(summary = "异步执行工作流中的指定节点", operationId = "orange-ai:workflows:add")
    @PostMapping("/{workflowId}/nodes/{nodeId}/run")
    public Result<WorkflowRunVO> runSingleNodeAsync(@PathVariable String workflowId,
                                                    @PathVariable String nodeId,
                                                    @RequestBody @Validated WorkflowRunParam param) {
        return ResultWrapper.ok(workflowRunService.runSingleNodeAsync(workflowId, nodeId, param));
    }

    @Operation(summary = "同步执行工作流中的指定节点", operationId = "orange-ai:workflows:add")
    @PostMapping("/{workflowId}/nodes/{nodeId}/run-sync")
    public Result<WorkflowRunVO> runSingleNodeSync(@PathVariable String workflowId,
                                                   @PathVariable String nodeId,
                                                   @RequestBody @Validated WorkflowRunParam param) {
        return ResultWrapper.ok(workflowRunService.runSingleNodeSync(workflowId, nodeId, param));
    }

    @Operation(summary = "根据ID查询执行详情", operationId = "orange-ai:workflows:get")
    @GetMapping("/{id}/detail")
    public Result<WorkflowRunDetailVO> getDetailById(@Parameter(description = "运行ID") @PathVariable("id") String id) {
        return ResultWrapper.ok(workflowRunService.getDetailById(id));
    }


//    @Operation(summary = "大模型节点调用", operationId = "orange-ai:workflows:add")
//    @PostMapping("/llm/invoke")
//    public Result<String> invokeLlmNode(@RequestBody @Validated AddWorkflowParam param) {
//        return ResultWrapper.ok(workflowRunService.invokeLlmNode(param));
//    }


}
