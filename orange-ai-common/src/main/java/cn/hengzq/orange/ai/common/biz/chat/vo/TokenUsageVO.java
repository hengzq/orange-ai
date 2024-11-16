package cn.hengzq.orange.ai.common.biz.chat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Schema(description = "AI - Token 用量VO")
@NoArgsConstructor
@AllArgsConstructor
public class TokenUsageVO {

    @Schema(description = "输入使用Token数量")
    private Long promptTokens;

    @Schema(description = "输出使用Token数量")
    private Long generationTokens;

    @Schema(description = "总使用Token数量")
    private Long totalTokens;

    public Long getTotalTokens() {
        return promptTokens + generationTokens;
    }
}
