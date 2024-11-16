package cn.hengzq.orange.ai.common.biz.chat.vo.param;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "会话管理-新增参数")
public class AddChatSessionParam implements Serializable {


    @Schema(description = "用户ID")
    private Long userId;


    @Schema(description = "会话名称")
    private String name;


    @Schema(description = "所属平台")
    private PlatformEnum platform;


    @Schema(description = "模型编码")
    private String modelCode;

}
