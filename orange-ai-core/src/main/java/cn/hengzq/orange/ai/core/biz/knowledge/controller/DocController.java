package cn.hengzq.orange.ai.core.biz.knowledge.controller;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.DocSplitVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.DocVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.WebContentVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.*;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.knowledge.service.DocService;
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
@Tag(name = "AI - 知识库管理 - 知识文档")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/knowledge-base-docs")
public class DocController {

    private final DocService docService;

    @Operation(summary = "文本内容保存为知识", operationId = "orange-ai:knowledge-doc:add-text-to-knowledge")
    @PostMapping("add-text-to-knowledge")
    public Result<String> addTextToKnowledge(@RequestBody @Validated AddTextToKnowledgeParam param) {
        return ResultWrapper.ok(docService.addTextToKnowledge(param));
    }

    @Operation(summary = "批量添加文档及文档块", operationId = "orange-ai:knowledge-doc:batch-add")
    @PostMapping("batch-add")
    public Result<Boolean> batchAdd(@RequestBody @Validated AddDocAndChunkParam param) {
        return ResultWrapper.ok(docService.batchAdd(param));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:knowledge-doc:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteById(@PathVariable("id") String id) {
        return ResultWrapper.ok(docService.deleteById(id));
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:knowledge-doc:get")
    @GetMapping("/{id}")
    public Result<DocVO> getById(@PathVariable("id") String id) {
        return ResultWrapper.ok(docService.getById(id));
    }

    @Operation(summary = "根据URL获取网页内容并转换为Markdown", operationId = "orange-ai:knowledge-doc:get")
    @PostMapping("/markdown-from-url")
    public Result<WebContentVO> getMarkdownFromUrl(@RequestBody @Validated UrlParam param) {
        return ResultWrapper.ok(docService.getMarkdownFromUrl(param));
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:knowledge-doc:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<DocVO>> page(@RequestBody KnowledgeDocumentPageParam param) {
        PageDTO<DocVO> result = docService.page(param);
        return ResultWrapper.ok(result);
    }

    @Operation(summary = "文档切割分段", operationId = "orange-ai:knowledge-doc:split")
    @PostMapping("split")
    public Result<List<DocSplitVO>> split(@RequestBody @Validated DocSplitParam param) {
        return ResultWrapper.ok(docService.split(param));
    }

}
