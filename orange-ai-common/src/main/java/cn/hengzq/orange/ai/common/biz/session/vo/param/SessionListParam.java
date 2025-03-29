package cn.hengzq.orange.ai.common.biz.session.vo.param;

import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "会话管理 - 分页查询参数")
public class SessionListParam implements Serializable {

    @Schema(description = "模型名称,模糊匹配")
    private String name;

    @Schema(description = "会话来源")
    private SessionTypeEnum sessionType;

    @Schema(description = "模型ID")
    private String modelId;

    @Schema(description = "关联外键ID")
    private Long associationId;

}
