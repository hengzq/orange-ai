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
@Schema(description = "模型管理 - 查询所有的数据")
public class ModelListParam implements Serializable {

    @Schema(description = "模型所属平台")
    private PlatformEnum platform;

    @Schema(description = "模型类别")
    private ModelTypeEnum type;

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "模型名称,模糊匹配")
    private String nameLike;

    @Schema(description = "模型启用状态 true:启用 false：不启用")
    private Boolean enabled;

}
