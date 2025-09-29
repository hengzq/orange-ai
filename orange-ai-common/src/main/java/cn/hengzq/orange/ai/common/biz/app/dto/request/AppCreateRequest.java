package cn.hengzq.orange.ai.common.biz.app.dto.request;

import cn.hengzq.orange.ai.common.biz.app.constant.AppTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "应用 - 创建参数")
public class AppCreateRequest implements Serializable {

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "应用类型")
    private AppTypeEnum appType;

    @Schema(description = "模型描述")
    private String description;
}
