package cn.hengzq.orange.ai.core.biz.model.controller;


import cn.hengzq.orange.ai.common.biz.model.dto.ModelResponse;
import cn.hengzq.orange.ai.common.biz.model.dto.param.ModelCreateRequest;
import cn.hengzq.orange.ai.common.biz.model.dto.param.ModelPageRequest;
import cn.hengzq.orange.ai.common.biz.model.dto.param.ModelQueryRequest;
import cn.hengzq.orange.ai.common.biz.model.dto.param.ModelUpdateRequest;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author hengzq
 */
@Tag(name = "AI - 模型管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/models")
public class ModelController {

    private final ModelService modelService;

    @Operation(summary = "新建", operationId = "orange-ai:model:add")
    @PostMapping
    public ApiResponse<String> createModel(@RequestBody @Validated ModelCreateRequest request) {
        return ApiResponse.ok(modelService.createModel(request));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:model:delete")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteModelById(@PathVariable("id") String id) {
        modelService.deleteModelById(id);
        return ApiResponse.ok();
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:model:update")
    @PutMapping("/{id}")
    public ApiResponse<Void> updateModelById(@PathVariable("id") String id, @RequestBody @Validated ModelUpdateRequest request) {
        modelService.updateModelById(id, request);
        return ApiResponse.ok();
    }

    @Operation(summary = "根据ID启用或禁用", operationId = "orange-ai:model:update")
    @PutMapping("/{id}/{enabled}")
    public ApiResponse<Void> updateEnabledById(@Parameter(description = "版本ID") @PathVariable("id") String id,
                                               @Parameter(description = "启用状态 true:启用 false：不启用") @PathVariable("enabled") boolean enabled) {
        modelService.updateEnabledById(id, enabled);
        return ApiResponse.ok();
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:model:get")
    @GetMapping("/{id}")
    public ApiResponse<ModelResponse> getModelById(@PathVariable("id") String id) {
        Optional<ModelResponse> optional = modelService.getModelById(id);
        return ApiResponse.ok(optional.orElse(null));
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:model:page")
    @PostMapping(value = "/page")
    public ApiResponse<PageDTO<ModelResponse>> pageModels(@RequestBody ModelPageRequest param) {
        PageDTO<ModelResponse> page = modelService.page(param);
        return ApiResponse.ok(page);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:model:list", description = "返回所有的数据")
    public ApiResponse<List<ModelResponse>> listModels(@RequestBody ModelQueryRequest query) {
        List<ModelResponse> list = modelService.list(query);
        return ApiResponse.ok(list);
    }
}
