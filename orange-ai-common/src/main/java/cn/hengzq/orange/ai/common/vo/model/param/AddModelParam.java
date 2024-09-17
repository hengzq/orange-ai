package cn.hengzq.orange.ai.common.vo.model.param;

import cn.hengzq.orange.ai.common.constant.ModelTypeEnum;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "模型管理 - 新增参数")
public class AddModelParam implements Serializable {

    @Schema(description = "模型所属平台")
    private PlatformEnum platform;

    @Schema(description = "模型类别")
    private ModelTypeEnum type;

//    @NotBlank(message = ModelErrorCode.BUTTON_PERMISSION_CANNOT_REPEAT.getCode())
    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "模型名称")
    private String code;

    @Schema(description = "模型启用状态 true:启用 false：不启用")
    private boolean enabled;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "模型描述")
    @Size(max = 1000, message = "长度必须小于等于500")
    private String description;
}
