package cn.hengzq.orange.ai.common.biz.session.vo.param;

import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会话管理-新增参数")
public class AddSessionParam implements Serializable {

    @Schema(description = "会话名称")
    private String name;

    @Schema(description = "模型ID")
    private String modelId;

    @Schema(description = "关联外键ID")
    private String associationId;

    @Schema(description = "会话类型")
    private SessionTypeEnum sessionType;

}
