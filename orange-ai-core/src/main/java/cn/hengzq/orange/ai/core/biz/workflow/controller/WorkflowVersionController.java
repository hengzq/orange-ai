package cn.hengzq.orange.ai.core.biz.workflow.controller;


import cn.hengzq.orange.ai.common.biz.app.vo.AppVersionVO;
import cn.hengzq.orange.ai.common.biz.app.vo.param.AppVersionPageParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowVersionVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.WorkflowVersionListParam;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.workflow.service.WorkflowVersionService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hengzq
 */
@Tag(name = "AI - 工作流 - 版本管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/workflow-versions")
public class WorkflowVersionController {

    private final WorkflowVersionService workflowVersionService;

    @Operation(summary = "分页查询", operationId = "orange-ai:app-version:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<AppVersionVO>> page(@RequestBody AppVersionPageParam param) {
        PageDTO<AppVersionVO> result = workflowVersionService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:workflows:list", description = "返回所有的数据")
    public Result<List<WorkflowVersionVO>> list(@RequestBody WorkflowVersionListParam param) {
        List<WorkflowVersionVO> list = workflowVersionService.list(param);
        return ResultWrapper.ok(list);
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:app:get")
    @GetMapping("/{id}")
    public Result<WorkflowVersionVO> getById(@Parameter(description = "版本ID") @PathVariable("id") String id) {
        return ResultWrapper.ok(workflowVersionService.getWorkflowVersionById(id));
    }
}
