package cn.hengzq.orange.ai.common.biz.chat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "交谈信息")
public class ConversationMessageVO {

    @Schema(description = "交谈内容")
    private String content;


}
