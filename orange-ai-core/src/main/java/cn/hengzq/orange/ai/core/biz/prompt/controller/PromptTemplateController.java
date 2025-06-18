package cn.hengzq.orange.ai.core.biz.prompt.controller;


import cn.hengzq.orange.ai.common.biz.model.vo.param.AddModelParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.ModelListParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.ModelPageParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.UpdateModelParam;
import cn.hengzq.orange.ai.common.biz.prompt.vo.PromptTemplateVO;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.AddPromptTemplateParam;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.PromptTemplateListParam;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.PromptTemplatePageParam;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.UpdatePromptTemplateParam;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.prompt.service.PromptTemplateService;
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
@Tag(name = "AI - 提示词模板")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/prompt-template")
public class PromptTemplateController {

    private final PromptTemplateService promptTemplateService;

    @Operation(summary = "新建", operationId = "orange-ai:prompt-template:add")
    @PostMapping
    public Result<String> add(@RequestBody @Validated AddPromptTemplateParam request) {
        return ResultWrapper.ok(promptTemplateService.add(request));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:prompt-template:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable("id") String id) {
        return ResultWrapper.ok(promptTemplateService.removeById(id));
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:prompt-template:update")
    @PutMapping("/{id}")
    public Result<Boolean> updateById(@PathVariable("id") String id, @RequestBody @Validated UpdatePromptTemplateParam request) {
        return ResultWrapper.ok(promptTemplateService.updateById(id, request));
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:prompt-template:get")
    @GetMapping("/{id}")
    public Result<PromptTemplateVO> getById(@PathVariable("id") String id) {
        return ResultWrapper.ok(promptTemplateService.getById(id));
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:prompt-template:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<PromptTemplateVO>> page(@RequestBody PromptTemplatePageParam param) {
        PageDTO<PromptTemplateVO> result = promptTemplateService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:prompt-template:list", description = "返回所有的数据")
    public Result<List<PromptTemplateVO>> list(@RequestBody PromptTemplateListParam param) {
        List<PromptTemplateVO> list = promptTemplateService.list(param);
        return ResultWrapper.ok(list);
    }
}
