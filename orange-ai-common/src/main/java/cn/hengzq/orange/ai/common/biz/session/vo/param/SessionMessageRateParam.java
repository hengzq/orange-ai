package cn.hengzq.orange.ai.common.biz.session.vo.param;

import cn.hengzq.orange.ai.common.biz.chat.constant.RatingEnum;
import cn.hengzq.orange.common.dto.param.IdParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "会话管理-记录评价")
public class SessionMessageRateParam extends IdParam {

    @Schema(description = "评价结果")
    private RatingEnum rating;

}
