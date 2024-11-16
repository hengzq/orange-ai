package cn.hengzq.orange.ai.common.biz.platform.vo.param;

import cn.hengzq.orange.ai.common.constant.ModelTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "平台管理 - 列表查询")
public class PlatformListParam implements Serializable {

    @Schema(description = "模型类型")
    private ModelTypeEnum modelType;

}
