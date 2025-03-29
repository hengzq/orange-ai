package cn.hengzq.orange.ai.core.biz.agent.controller;


import cn.hengzq.orange.ai.common.biz.agent.vo.AgentVO;
import cn.hengzq.orange.ai.common.biz.agent.vo.ConversationStreamVO;
import cn.hengzq.orange.ai.common.biz.agent.vo.param.*;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.agent.service.AgentService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "AI - 智能体管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/agent")
public class AgentController {

    private final AgentService agentService;

    @Operation(summary = "新建", operationId = "orange-ai:model:add")
    @PostMapping
    public Result<String> add(@RequestBody @Validated AddAgentParam request) {
        return ResultWrapper.ok(agentService.add(request));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:model:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable("id") String id) {
        return ResultWrapper.ok(agentService.removeById(id));
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:model:update")
    @PutMapping("/{id}")
    public Result<Boolean> updateById(@PathVariable("id") String id, @RequestBody @Validated UpdateAgentParam request) {
        return ResultWrapper.ok(agentService.updateById(id, request));
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:model:get")
    @GetMapping("/{id}")
    public Result<AgentVO> getById(@PathVariable("id") String id) {
        return ResultWrapper.ok(agentService.getById(id));
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:model:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<AgentVO>> page(@RequestBody AgentPageParam param) {
        PageDTO<AgentVO> result = agentService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:model:list", description = "返回所有的数据")
    public Result<List<AgentVO>> list(@RequestBody AgentListParam query) {
        List<AgentVO> list = agentService.list(query);
        return ResultWrapper.ok(list);
    }

    @Operation(summary = "跟AI进行对话交流（流式）（用于智能体调试）", description = "流式返回，响应较快")
    @PostMapping(value = "/debug-conversation-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Result<ConversationStreamVO>> debugConversationStream(@RequestBody AgentDebugConversationParam param) {
        return agentService.debugConversationStream(param);
    }
}
