package cn.hengzq.orange.ai.common.biz.session.vo.param;

import cn.hengzq.orange.common.dto.param.IdParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "会话管理-修改参数")
public class UpdateSessionParam extends IdParam {

    @Schema(description = "会话名称")
    private String name;

}
