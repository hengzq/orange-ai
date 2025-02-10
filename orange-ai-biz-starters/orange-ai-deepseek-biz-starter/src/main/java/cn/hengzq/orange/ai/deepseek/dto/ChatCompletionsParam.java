package cn.hengzq.orange.ai.deepseek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 对话补全 参数
 * 参考：<a href="https://api-docs.deepseek.com/zh-cn/api/create-chat-completion">...</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "对话补全 请求参数")
public class ChatCompletionsParam {

    @Schema(description = "使用的模型的 ID")
    private String model;

    @Schema(description = "对话的消息列表。")
    private List<MessageItem> messages;

    @Schema(description = "如果设置为 True，将会以 SSE（server-sent events）的形式以流式发送消息增量。消息流以 data: [DONE] 结尾。")
    private Boolean stream;
}
