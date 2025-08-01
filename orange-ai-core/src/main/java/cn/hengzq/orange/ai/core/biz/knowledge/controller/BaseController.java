package cn.hengzq.orange.ai.core.biz.knowledge.controller;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.BaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBaseListParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBasePageParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.knowledge.service.BaseService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hengzq
 */
@Tag(name = "AI - 知识库管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/knowledge-bases")
public class BaseController {

    private final BaseService baseService;

    @Operation(summary = "新建", operationId = "orange-ai:knowledge:add")
    @PostMapping
    public Result<String> add(@RequestBody @Validated AddKnowledgeBaseParam param) {
        return ResultWrapper.ok(baseService.add(param));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:knowledge:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable("id") String id) {
        return ResultWrapper.ok(baseService.removeById(id));
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:knowledge:update")
    @PutMapping("/{id}")
    public Result<Boolean> updateById(@PathVariable("id") String id, @RequestBody @Validated UpdateKnowledgeBaseParam param) {
        return ResultWrapper.ok(baseService.updateById(id, param));
    }

    @Operation(summary = "根据ID启用或禁用", operationId = "orange-ai:knowledge:update")
    @PutMapping("/{id}/{enabled}")
    public Result<Boolean> updateEnabledById(@Parameter(description = "版本ID") @PathVariable("id") String id,
                                             @Parameter(description = "启用状态 true:启用 false：不启用") @PathVariable("enabled") boolean enabled) {
        return ResultWrapper.ok(baseService.updateEnabledById(id, enabled));
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:knowledge:get")
    @GetMapping("/{id}")
    public Result<BaseVO> getById(@PathVariable("id") String id) {
        return ResultWrapper.ok(baseService.getById(id));
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:knowledge:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<BaseVO>> page(@RequestBody KnowledgeBasePageParam param) {
        PageDTO<BaseVO> result = baseService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:knowledge:list", description = "返回所有的数据")
    public Result<List<BaseVO>> list(@RequestBody KnowledgeBaseListParam param) {
        List<BaseVO> list = baseService.list(param);
        return ResultWrapper.ok(list);
    }
}
