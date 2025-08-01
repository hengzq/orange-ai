package cn.hengzq.orange.ai.core.biz.knowledge.controller;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.ChunkVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddDocSliceParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeDocSliceListParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeDocSlicePageParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateDocSliceParam;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.knowledge.service.ChunkService;
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
@Tag(name = "AI - 知识库管理 - 知识文档 - 文档块")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/knowledge-base-doc-chunks")
public class ChunkController {

    private final ChunkService chunkService;

    @Operation(summary = "新建", operationId = "orange-ai:knowledge-doc-slice:add")
    @PostMapping
    public Result<String> add(@RequestBody @Validated AddDocSliceParam param) {
        return ResultWrapper.ok(chunkService.add(param));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:knowledge-doc-slice:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable("id") String id) {
        return ResultWrapper.ok(chunkService.deleteById(id));
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:knowledge-doc-slice:update")
    @PutMapping("/{id}")
    public Result<Boolean> updateById(@PathVariable("id") String id, @RequestBody @Validated UpdateDocSliceParam param) {
        return ResultWrapper.ok(chunkService.updateById(id, param));
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:knowledge-doc-slice:get")
    @GetMapping("/{id}")
    public Result<ChunkVO> getById(@PathVariable("id") String id) {
        return ResultWrapper.ok(chunkService.getById(id));
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:knowledge-doc-slice:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<ChunkVO>> page(@RequestBody KnowledgeDocSlicePageParam param) {
        PageDTO<ChunkVO> result = chunkService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:knowledge-doc-slice:list", description = "返回所有的数据")
    public Result<List<ChunkVO>> list(@RequestBody KnowledgeDocSliceListParam param) {
        List<ChunkVO> list = chunkService.list(param);
        return ResultWrapper.ok(list);
    }
}
