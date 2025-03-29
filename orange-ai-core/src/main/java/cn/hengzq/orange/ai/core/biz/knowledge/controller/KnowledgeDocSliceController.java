package cn.hengzq.orange.ai.core.biz.knowledge.controller;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeDocSliceVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.*;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.knowledge.service.KnowledgeDocSliceService;
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
@Tag(name = "AI - 知识库管理 - 知识文档 - 切片")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/knowledge-doc-slice")
public class KnowledgeDocSliceController {

    private final KnowledgeDocSliceService knowledgeDocSliceService;

    @Operation(summary = "新建", operationId = "orange-ai:knowledge-doc-slice:add")
    @PostMapping
    public Result<String> add(@RequestBody @Validated AddDocSliceParam param) {
        return ResultWrapper.ok(knowledgeDocSliceService.add(param));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:knowledge-doc-slice:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable("id") String id) {
        return ResultWrapper.ok(knowledgeDocSliceService.deleteById(id));
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:knowledge-doc-slice:update")
    @PutMapping("/{id}")
    public Result<Boolean> updateById(@PathVariable("id") String id, @RequestBody @Validated UpdateDocSliceParam param) {
        return ResultWrapper.ok(knowledgeDocSliceService.updateById(id, param));
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:knowledge-doc-slice:get")
    @GetMapping("/{id}")
    public Result<KnowledgeDocSliceVO> getById(@PathVariable("id") String id) {
        return ResultWrapper.ok(knowledgeDocSliceService.getById(id));
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:knowledge-doc-slice:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<KnowledgeDocSliceVO>> page(@RequestBody KnowledgeDocSlicePageParam param) {
        PageDTO<KnowledgeDocSliceVO> result = knowledgeDocSliceService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:knowledge-doc-slice:list", description = "返回所有的数据")
    public Result<List<KnowledgeDocSliceVO>> list(@RequestBody KnowledgeDocSliceListParam param) {
        List<KnowledgeDocSliceVO> list = knowledgeDocSliceService.list(param);
        return ResultWrapper.ok(list);
    }
}
