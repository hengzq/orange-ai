package cn.hengzq.orange.ai.core.biz.app.controller;


import cn.hengzq.orange.ai.common.biz.app.vo.AppVO;
import cn.hengzq.orange.ai.common.biz.app.vo.param.*;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.app.service.AppService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author hengzq
 */
@Tag(name = "AI - 应用")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/apps")
public class AppController {

    private final AppService appService;

    @Operation(summary = "新建", operationId = "orange-ai:app:add")
    @PostMapping
    public Result<String> add(@RequestBody @Validated AddAppParam request) {
        return ResultWrapper.ok(appService.add(request));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:app:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable("id") String id) {
        return ResultWrapper.ok(appService.removeById(id));
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:app:update")
    @PutMapping("/{id}")
    public Result<Boolean> updateById(@PathVariable("id") String id, @RequestBody @Validated UpdateAppParam request) {
        return ResultWrapper.ok(appService.updateById(id, request));
    }

    @Operation(summary = "根据应用ID发布应用", operationId = "orange-ai:app:update")
    @PutMapping("/{id}/publish")
    public Result<Boolean> updatePublishById(@PathVariable("id") String id) {
        return ResultWrapper.ok(appService.updatePublishById(id));
    }


    @Operation(summary = "根据ID查询最新数据", operationId = "orange-ai:app:get")
    @GetMapping("/{id}/latest")
    public Result<AppVO> getLatestById(@PathVariable("id") String id,
                                       @Parameter(description = "是否获取最新发布版本 true:最新发布版, false:最新草稿版,如果没有草稿版，返回最新发布版本）") @RequestParam(required = false) boolean latestReleased) {
        return ResultWrapper.ok(appService.getLatestById(id, latestReleased));
    }


    @Operation(summary = "分页查询", operationId = "orange-ai:app:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<AppVO>> page(@RequestBody WorkflowPageParam param) {
        PageDTO<AppVO> result = appService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:app:list", description = "返回所有的数据")
    public Result<List<AppVO>> list(@RequestBody AppListParam query) {
        List<AppVO> list = appService.list(query);
        return ResultWrapper.ok(list);
    }


    @Operation(summary = "对话交流（流式返回）", description = "流式返回，响应较快")
    @PostMapping(value = "/conversation-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Result<ConversationResponse>> conversationStream(@RequestBody AppConversationStreamParam param) {
        return appService.conversationStream(param);
    }
}
