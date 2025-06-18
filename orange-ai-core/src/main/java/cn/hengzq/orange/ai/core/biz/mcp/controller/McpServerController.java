package cn.hengzq.orange.ai.core.biz.mcp.controller;


import cn.hengzq.orange.ai.common.biz.mcp.vo.McpServerVO;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.AddMcpServerParam;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.McpServerListParam;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.McpServerPageParam;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.UpdateMcpServerParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.AddModelParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.ModelListParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.ModelPageParam;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.mcp.service.McpServerService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hengzq
 */
@Tag(name = "AI - Mcp 服务管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/mcp-server")
public class McpServerController {

    private final McpServerService mcpServerService;

    @Operation(summary = "新建", operationId = "orange-ai:mcp-server:add")
    @PostMapping
    public Result<String> add(@RequestBody @Validated AddMcpServerParam request) {
        return ResultWrapper.ok(mcpServerService.add(request));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:mcp-server:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable("id") String id) {
        return ResultWrapper.ok(mcpServerService.removeById(id));
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:mcp-server:update")
    @PutMapping("/{id}")
    public Result<Boolean> updateById(@PathVariable("id") String id, @RequestBody @Validated UpdateMcpServerParam request) {
        return ResultWrapper.ok(mcpServerService.updateById(id, request));
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:mcp-server:get")
    @GetMapping("/{id}")
    public Result<McpServerVO> getById(@PathVariable("id") String id) {
        return ResultWrapper.ok(mcpServerService.getById(id));
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:mcp-server:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<McpServerVO>> page(@RequestBody McpServerPageParam param) {
        PageDTO<McpServerVO> result = mcpServerService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:mcp-server:list", description = "返回所有的数据")
    public Result<List<McpServerVO>> list(@RequestBody McpServerListParam query) {
        List<McpServerVO> list = mcpServerService.list(query);
        return ResultWrapper.ok(list);
    }
}
