package cn.hengzq.orange.ai.common.biz.app.vo.param;

import cn.hengzq.orange.common.dto.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "应用版本管理 - 查询所有的数据")
public class AppVersionPageParam extends PageParam {

    @Schema(description = "应用ID")
    private String appId;

}
