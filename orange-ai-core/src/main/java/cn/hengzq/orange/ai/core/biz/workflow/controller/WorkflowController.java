package cn.hengzq.orange.ai.core.biz.workflow.controller;


import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowDetailVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowListResponse;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.UpdateWorkflowGraphParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowCreateRequest;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowPageRequest;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowUpdateRequest;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowRunService;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author hengzq
 */
@Tag(name = "AI - 工作流")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/workflows")
public class WorkflowController {

    private final WorkflowService workflowService;

    private final WorkflowRunService workflowRunService;

    @Operation(summary = "新建", operationId = "orange-ai:workflow:add")
    @PostMapping
    public ApiResponse<String> createWorkflow(@RequestBody @Validated WorkflowCreateRequest request) {
        return ApiResponse.ok(workflowService.createWorkflow(request));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:workflows:delete")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteWorkflowById(@PathVariable("id") String id) {
        workflowService.deleteWorkflowById(id);
        return ApiResponse.ok();
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:workflows:update")
    @PutMapping("/{id}")
    public ApiResponse<Void> updateById(@PathVariable("id") String id, @RequestBody @Validated WorkflowUpdateRequest request) {
        workflowService.updateById(id, request);
        return ApiResponse.ok();
    }


    @Operation(summary = "根据ID更新流程图", operationId = "orange-ai:workflows:update")
    @PutMapping("/{id}/graph")
    public ApiResponse<Boolean> updateGraphById(@PathVariable("id") String id, @RequestBody @Validated UpdateWorkflowGraphParam param) {
        return ApiResponse.ok(workflowService.updateGraphById(id, param));
    }


    @Operation(summary = "根据工作流ID发布应用", operationId = "orange-ai:workflows:update")
    @PutMapping("/{id}/publish")
    public ApiResponse<Boolean> updatePublishById(@Parameter(description = "工作流ID") @PathVariable("id") String id) {
        return ApiResponse.ok(workflowService.updatePublishById(id));
    }


    @Operation(summary = "根据ID查询", operationId = "orange-ai:workflows:get")
    @GetMapping("/{id}")
    public ApiResponse<WorkflowVO> getById(@Parameter(description = "工作流ID") @PathVariable("id") String id,
                                           @Parameter(description = "是否获取最新发布版本 true:最新发布版, false:最新草稿版,如果没有草稿版，返回最新发布版本）") @RequestParam(required = false) boolean latestReleased) {
        return ApiResponse.ok(workflowService.getById(id, latestReleased));
    }

    @Operation(summary = "根据ID查询详情信息", operationId = "orange-ai:workflows:get")
    @GetMapping("/{id}/detail")
    public ApiResponse<WorkflowDetailVO> getDetailById(@Parameter(description = "工作流ID") @PathVariable("id") String id,
                                                       @Parameter(description = "是否获取最新发布版本 true:最新发布版, false:最新草稿版,如果没有草稿版，返回最新发布版本）") @RequestParam(required = false) boolean latestReleased) {
        return ApiResponse.ok(workflowService.getDetailById(id, latestReleased));
    }


    @Operation(summary = "分页查询", operationId = "orange-ai:workflow:page")
    @PostMapping(value = "/page")
    public ApiResponse<PageDTO<WorkflowListResponse>> pageWorkflows(@RequestBody WorkflowPageRequest request) {
        return ApiResponse.ok(workflowService.pageWorkflows(request));
    }

}
