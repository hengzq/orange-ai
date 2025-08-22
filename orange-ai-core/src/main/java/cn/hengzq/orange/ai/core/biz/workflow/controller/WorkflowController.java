package cn.hengzq.orange.ai.core.biz.workflow.controller;


import cn.hengzq.orange.ai.common.biz.app.vo.param.WorkflowPageParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowDetailVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.WorkflowVO;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.AddWorkflowParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.WorkflowRunParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.UpdateWorkflowGraphParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.param.UpdateWorkflowParam;
import cn.hengzq.orange.ai.common.biz.workflow.vo.result.WorkflowRunResult;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowRunService;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
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

    @Operation(summary = "新建", operationId = "orange-ai:workflows:add")
    @PostMapping
    public Result<String> create(@RequestBody @Validated AddWorkflowParam param) {
        return ResultWrapper.ok(workflowService.create(param));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:workflows:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable("id") String id) {
        return ResultWrapper.ok(workflowService.removeById(id));
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:workflows:update")
    @PutMapping("/{id}")
    public Result<Boolean> updateById(@PathVariable("id") String id, @RequestBody @Validated UpdateWorkflowParam param) {
        return ResultWrapper.ok(workflowService.updateById(id, param));
    }


    @Operation(summary = "根据ID更新流程图", operationId = "orange-ai:workflows:update")
    @PutMapping("/{id}/graph")
    public Result<Boolean> updateGraphById(@PathVariable("id") String id, @RequestBody @Validated UpdateWorkflowGraphParam param) {
        return ResultWrapper.ok(workflowService.updateGraphById(id, param));
    }


    @Operation(summary = "根据工作流ID发布应用", operationId = "orange-ai:workflows:update")
    @PutMapping("/{id}/publish")
    public Result<Boolean> updatePublishById(@Parameter(description = "工作流ID") @PathVariable("id") String id) {
        return ResultWrapper.ok(workflowService.updatePublishById(id));
    }


    @Operation(summary = "根据ID查询", operationId = "orange-ai:workflows:get")
    @GetMapping("/{id}")
    public Result<WorkflowVO> getById(@Parameter(description = "工作流ID") @PathVariable("id") String id,
                                      @Parameter(description = "是否获取最新发布版本 true:最新发布版, false:最新草稿版,如果没有草稿版，返回最新发布版本）") @RequestParam(required = false) boolean latestReleased) {
        return ResultWrapper.ok(workflowService.getById(id, latestReleased));
    }

    @Operation(summary = "根据ID查询详情信息", operationId = "orange-ai:workflows:get")
    @GetMapping("/{id}/detail")
    public Result<WorkflowDetailVO> getDetailById(@Parameter(description = "工作流ID") @PathVariable("id") String id,
                                                  @Parameter(description = "是否获取最新发布版本 true:最新发布版, false:最新草稿版,如果没有草稿版，返回最新发布版本）") @RequestParam(required = false) boolean latestReleased) {
        return ResultWrapper.ok(workflowService.getDetailById(id, latestReleased));
    }


    @Operation(summary = "分页查询", operationId = "orange-ai:workflow:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<WorkflowVO>> page(@RequestBody WorkflowPageParam param) {
        PageDTO<WorkflowVO> result = workflowService.page(param);
        return ResultWrapper.ok(result);
    }

//    @PostMapping(value = "/list")
//    @Operation(summary = "查询所有的数据", operationId = "orange-ai:workflows:list", description = "返回所有的数据")
//    public Result<List<AppVO>> list(@RequestBody AppListParam query) {
//        List<AppVO> list = workflowService.list(query);
//        return ResultWrapper.ok(list);
//    }

}
