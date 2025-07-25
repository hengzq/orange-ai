package cn.hengzq.orange.ai.common.biz.app.vo.param;

import cn.hengzq.orange.ai.common.biz.app.vo.AppModelVO;
import cn.hengzq.orange.ai.common.biz.app.vo.AppVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "应用管理 - 更新关联参数")
public class UpdateAppWithRelationsParam implements Serializable {

    @Schema(description = "应用")
    private AppVO app;

    @Schema(description = "关联模型")
    private AppModelVO model;


}
