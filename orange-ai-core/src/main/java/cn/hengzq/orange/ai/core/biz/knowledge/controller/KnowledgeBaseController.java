package cn.hengzq.orange.ai.core.biz.knowledge.controller;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeBaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBaseListParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBasePageParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateKnowledgeBaseParam;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.knowledge.service.KnowledgeBaseService;
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
@Tag(name = "AI - 知识库管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/knowledge-base")
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;

    @Operation(summary = "新建", operationId = "orange-ai:knowledge:add")
    @PostMapping
    public Result<String> add(@RequestBody @Validated AddKnowledgeBaseParam param) {
        return ResultWrapper.ok(knowledgeBaseService.add(param));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:knowledge:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable("id") String id) {
        return ResultWrapper.ok(knowledgeBaseService.removeById(id));
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:knowledge:update")
    @PutMapping("/{id}")
    public Result<Boolean> updateById(@PathVariable("id") String id, @RequestBody @Validated UpdateKnowledgeBaseParam param) {
        return ResultWrapper.ok(knowledgeBaseService.updateById(id, param));
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:knowledge:get")
    @GetMapping("/{id}")
    public Result<KnowledgeBaseVO> getById(@PathVariable("id") String id) {
        return ResultWrapper.ok(knowledgeBaseService.getById(id));
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:knowledge:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<KnowledgeBaseVO>> page(@RequestBody KnowledgeBasePageParam param) {
        PageDTO<KnowledgeBaseVO> result = knowledgeBaseService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:knowledge:list", description = "返回所有的数据")
    public Result<List<KnowledgeBaseVO>> list(@RequestBody KnowledgeBaseListParam param) {
        List<KnowledgeBaseVO> list = knowledgeBaseService.list(param);
        return ResultWrapper.ok(list);
    }
}
