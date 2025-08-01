package cn.hengzq.orange.ai.common.biz.model.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "原始模型名称 ")
    private String modelName;

    @Schema(description = "模型启用状态 true:启用 false：不启用")
    private boolean enabled;

    @Schema(description = "基础URL eg:ollama路径为http://localhost:11434")
    private String baseUrl;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "模型描述")
    private String description;
}
