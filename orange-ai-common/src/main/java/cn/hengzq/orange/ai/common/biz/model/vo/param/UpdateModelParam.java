package cn.hengzq.orange.ai.common.biz.model.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "模型管理 - 更新参数")
public class UpdateModelParam implements Serializable {

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "模型名称")
    private String code;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "模型描述")
    private String description;
}
