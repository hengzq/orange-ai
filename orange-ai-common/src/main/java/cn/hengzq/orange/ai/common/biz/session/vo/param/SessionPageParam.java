package cn.hengzq.orange.ai.common.biz.session.vo.param;

import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
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
public class SessionPageParam extends PageParam {

    @Schema(description = "会话名称，模糊查询")
    private String name;

    @Schema(description = "会话来源")
    private SessionTypeEnum sessionType;

    @Schema(description = "模型ID")
    private String modelId;

    @Schema(description = "关联外键ID")
    private Long associationId;

}
