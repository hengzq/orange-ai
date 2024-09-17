package cn.hengzq.orange.ai.common.vo.model.param;

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
    @Size(max = 1000, message = "长度必须小于等于500")
    private String description;
}
