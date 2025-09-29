package cn.hengzq.orange.ai.core.biz.app.controller;


import cn.hengzq.orange.ai.common.biz.app.dto.AppVersionVO;
import cn.hengzq.orange.ai.common.biz.app.dto.request.AppVersionPageParam;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.app.service.AppVersionService;
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
@Tag(name = "AI - 应用 - 版本管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/apps-version")
public class AppVersionController {

    private final AppVersionService appVersionService;

    @Operation(summary = "分页查询", operationId = "orange-ai:app-version:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<AppVersionVO>> page(@RequestBody AppVersionPageParam param) {
        PageDTO<AppVersionVO> result = appVersionService.page(param);
        return ResultWrapper.ok(result);
    }

    @GetMapping(value = "/{appId}/list")
    @Operation(summary = "根据应用ID查询所有的数据", operationId = "orange-ai:app:list", description = "根据应用ID查询所有的数据")
    public Result<List<AppVersionVO>> listByAppId(@PathVariable("appId") String appId) {
        List<AppVersionVO> list = appVersionService.listByAppId(appId);
        return ResultWrapper.ok(list);
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:app:get")
    @GetMapping("/{id}")
    public Result<AppVersionVO> getById(@Parameter(description = "版本ID") @PathVariable("id") String id) {
        return ResultWrapper.ok(appVersionService.getById(id));
    }
}
