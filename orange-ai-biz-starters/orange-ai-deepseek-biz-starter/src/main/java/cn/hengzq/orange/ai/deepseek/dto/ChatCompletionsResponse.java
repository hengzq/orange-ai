package cn.hengzq.orange.ai.deepseek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 对话补全 返回参数
 * 参考：<a href="https://api-docs.deepseek.com/zh-cn/api/create-chat-completion">...</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "对话补全 返回参数")
public class ChatCompletionsResponse {

    @Schema(description = "该对话的唯一标识符")
    private String id;

    @Schema(description = "模型生成的 completion 的选择列表。")
    private List<ChoiceItem> choices;

    @Data
    public static class ChoiceItem {

        @Schema(description = "模型生成的 completion 消息。")
        private MessageItem delta;
    }
}

