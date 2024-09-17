package cn.hengzq.orange.ai.common.vo.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@Schema(description = "对话回复 VO")
public class ConversationReplyVO implements Serializable {


    @Schema(description = "回复内容")
    private String content;

    @Schema(description = "是否交谈结束 false：未结束（默认），true：结束 ")
    private boolean finished;

}
