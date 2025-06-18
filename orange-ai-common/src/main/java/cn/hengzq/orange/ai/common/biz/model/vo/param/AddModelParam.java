package cn.hengzq.orange.ai.common.biz.model.vo.param;

import cn.hengzq.orange.ai.common.constant.ModelTypeEnum;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private ModelTypeEnum modelType;

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "原始模型名称 ")
    private String modelName;

    @Schema(description = "模型启用状态 true:启用 false：不启用")
    private boolean enabled;

    @Schema(description = "基础URL eg:ollama路径为http://localhost:11434")
    private String baseUrl;

    @Schema(description = "API KEY")
    private String apiKey;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "模型描述")
    private String description;
}
