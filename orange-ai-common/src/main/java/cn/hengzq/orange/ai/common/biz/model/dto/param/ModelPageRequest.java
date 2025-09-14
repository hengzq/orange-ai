package cn.hengzq.orange.ai.common.biz.model.dto.param;

import cn.hengzq.orange.common.dto.param.PageParam;
import cn.hengzq.orange.ai.common.constant.ModelTypeEnum;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "模型管理 - 查询所有的数据")
public class ModelPageRequest extends PageParam {

    @Schema(description = "模型所属平台")
    private PlatformEnum platform;

    @Schema(description = "模型类别")
    private ModelTypeEnum modelType;

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "模型名称,模糊匹配")
    private String nameLike;

}
