package cn.hengzq.orange.ai.core.core.chat.controller;


import cn.hengzq.orange.ai.core.core.chat.service.ChatSessionService;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.common.vo.chat.ChatSessionVO;
import cn.hengzq.orange.ai.common.vo.chat.param.AddChatSessionParam;
import cn.hengzq.orange.ai.common.vo.chat.param.ChatSessionListParam;
import cn.hengzq.orange.ai.common.vo.chat.param.ChatSessionPageParam;
import cn.hengzq.orange.ai.common.vo.chat.param.UpdateChatSessionParam;
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
@Tag(name = "聊天会话 - 管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/chat-session")
public class ChatSessionController {

    private final ChatSessionService chatSessionService;

    @Operation(summary = "新建", operationId = "orange-ai:chat-session:add")
    @PostMapping
    public Result<Long> add(@RequestBody @Validated AddChatSessionParam request) {
        return ResultWrapper.ok(chatSessionService.add(request));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:chat-session:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable("id") Long id) {
        return ResultWrapper.ok(chatSessionService.removeById(id));
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:chat-session:update")
    @PutMapping("/{id}")
    public Result<Boolean> updateById(@PathVariable("id") Long id, @RequestBody @Validated UpdateChatSessionParam param) {
        return ResultWrapper.ok(chatSessionService.updateById(id, param));
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:chat-session:get")
    @GetMapping("/{id}")
    public Result<ChatSessionVO> getById(@PathVariable("id") Long id) {
        return ResultWrapper.ok(chatSessionService.getById(id));
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:chat-session:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<ChatSessionVO>> page(@RequestBody ChatSessionPageParam param) {
        PageDTO<ChatSessionVO> result = chatSessionService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:chat-session:list", description = "返回所有的数据")
    public Result<List<ChatSessionVO>> list(@RequestBody ChatSessionListParam param) {
        List<ChatSessionVO> list = chatSessionService.list(param);
        return ResultWrapper.ok(list);
    }
}
