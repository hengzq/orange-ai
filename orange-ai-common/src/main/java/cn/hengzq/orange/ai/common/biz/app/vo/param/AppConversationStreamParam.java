package cn.hengzq.orange.ai.common.biz.app.vo.param;

import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Schema(description = "应用 - 对话交流参数 VO")
public class AppConversationStreamParam {

    @Schema(description = "回话ID")
    private String sessionId;

    @Schema(description = "应用ID")
    private String appId;

    @Schema(description = "提示词")
    private String prompt;

    @Schema(description = "回话类型")
    private SessionTypeEnum sessionType;
}
