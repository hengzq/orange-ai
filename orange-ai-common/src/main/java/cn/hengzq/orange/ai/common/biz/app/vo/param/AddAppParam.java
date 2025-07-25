package cn.hengzq.orange.ai.common.biz.app.vo.param;

import cn.hengzq.orange.ai.common.biz.app.constant.AppTypeEnum;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hengzq
 */
@Data
@Schema(description = "应用 - 新增参数")
public class AddAppParam implements Serializable {

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "应用类型")
    private AppTypeEnum appType;

    @Schema(description = "模型描述")
    private String description;
}
