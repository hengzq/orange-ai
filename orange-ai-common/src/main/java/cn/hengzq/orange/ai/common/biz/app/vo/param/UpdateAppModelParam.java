package cn.hengzq.orange.ai.common.biz.app.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "应用 - 更新关联模型")
public class UpdateAppModelParam implements Serializable {

    @Schema(description = "模型ID")
    private String modelId;

}
