package cn.hengzq.orange.ai.common.vo.chat.param;

import cn.hengzq.orange.common.dto.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "会话管理-分页查询参数")
public class ChatSessionPageParam extends PageParam {

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "模型名称,模糊匹配")
    private String nameLike;

}
