package cn.hengzq.orange.ai.core.core.chat.controller;

import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.ai.core.constant.ModelEnum;
import cn.hengzq.orange.ai.core.core.chat.service.ChatService;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Tag(name = "聊天会话 - 沟通交流")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/chat")
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "跟AI进行对话交流(内容一次性返回)")
    @GetMapping("/conversation")
    public String conversation(String message) {
//        Prompt prompt = new Prompt(message, TongYiChatOptions.builder()
//                .withModel("qwen1.5-0.5b-chat")
//                .build());

        Prompt prompt = new Prompt(message, OllamaOptions.fromOptions(OllamaOptions.create().withModel("qwen2:1.5b")));
        String content = ModelEnum.getOllamaChatModel().call(prompt).getResult().getOutput().getContent();
        return content;
    }


    @Operation(summary = "跟AI进行对话交流（流式）", description = "流式返回，响应较快")
    @PostMapping(value = "/conversation-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Result<ConversationReplyVO>> conversationStream(@RequestBody ConversationParam param) {
        return chatService.conversationStream(param);
    }
}
