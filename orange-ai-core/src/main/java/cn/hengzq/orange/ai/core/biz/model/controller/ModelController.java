package cn.hengzq.orange.ai.core.biz.model.controller;


import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.common.vo.model.ModelVO;
import cn.hengzq.orange.ai.common.vo.model.param.AddModelParam;
import cn.hengzq.orange.ai.common.vo.model.param.ModelListParam;
import cn.hengzq.orange.ai.common.vo.model.param.ModelPageParam;
import cn.hengzq.orange.ai.common.vo.model.param.UpdateModelParam;
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
@Tag(name = "AI 大模型管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/model")
public class ModelController {

    private final ModelService modelService;

    @Operation(summary = "新建", operationId = "orange-ai:model:add")
    @PostMapping
    public Result<Long> add(@RequestBody @Validated AddModelParam request) {
        return ResultWrapper.ok(modelService.add(request));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:model:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable("id") Long id) {
        return ResultWrapper.ok(modelService.removeById(id));
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:model:update")
    @PutMapping("/{id}")
    public Result<Boolean> updateById(@PathVariable("id") Long id, @RequestBody @Validated UpdateModelParam request) {
        return ResultWrapper.ok(modelService.updateById(id, request));
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:model:get")
    @GetMapping("/{id}")
    public Result<ModelVO> getById(@PathVariable("id") Long id) {
        return ResultWrapper.ok(modelService.getById(id));
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:model:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<ModelVO>> page(@RequestBody ModelPageParam param) {
        PageDTO<ModelVO> result = modelService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:model:list", description = "返回所有的数据")
    public Result<List<ModelVO>> list(@RequestBody ModelListParam query) {
        List<ModelVO> list = modelService.list(query);
        return ResultWrapper.ok(list);
    }
}
