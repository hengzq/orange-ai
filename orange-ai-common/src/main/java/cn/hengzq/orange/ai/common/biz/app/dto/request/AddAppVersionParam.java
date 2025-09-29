package cn.hengzq.orange.ai.common.biz.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "应用版本 - 新增")
public class AddAppVersionParam implements Serializable {


    @Schema(description = "应用ID")
    private String appId;

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "系统提示词")
    private String systemPrompt;

    @Schema(description = "应用描述")
    private String description;


}
