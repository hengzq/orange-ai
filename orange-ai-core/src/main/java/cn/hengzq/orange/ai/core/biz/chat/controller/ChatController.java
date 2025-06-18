package cn.hengzq.orange.ai.core.biz.chat.controller;

import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.AgentConversationStreamParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.CompletionsParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ConversationStreamParam;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatService;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;


@Tag(name = "AI - 聊天会话")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/chat")
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "跟AI进行对话交流(内容一次性返回)")
    @GetMapping("/conversation")
    public Result<ConversationResponse> conversation(@RequestBody ConversationStreamParam param) {
        return ResultWrapper.ok(chatService.conversation(param));
    }

    @Operation(summary = "指定模型AI进行对话交流（流式返回）", description = "流式返回，响应较快")
    @PostMapping(value = "/conversation-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Result<ConversationResponse>> conversationStream(@RequestBody ConversationStreamParam param) {
        return chatService.conversationStream(param);
    }

    @Operation(summary = "智能体进行对话交流（流式返回）", description = "流式返回，响应较快")
    @PostMapping(value = "/agent-conversation-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Result<ConversationResponse>> agentConversationStream(@RequestBody AgentConversationStreamParam param) {
        return chatService.agentConversationStream(param);
    }


}
