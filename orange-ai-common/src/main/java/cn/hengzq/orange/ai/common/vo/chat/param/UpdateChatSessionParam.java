package cn.hengzq.orange.ai.common.vo.chat.param;

import cn.hengzq.orange.common.dto.param.IdParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "会话管理-修改参数")
public class UpdateChatSessionParam extends IdParam {


    @Schema(description = "会话名称")
    private String name;


}
