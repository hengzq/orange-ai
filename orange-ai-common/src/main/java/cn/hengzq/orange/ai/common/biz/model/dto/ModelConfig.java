package cn.hengzq.orange.ai.common.biz.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "模型配置")
public class ModelConfig implements Serializable {


    @Schema(description = "温度")
    private Double temperature;

    /**
     * 设置带入模型上下文的对话历史轮数。轮数越多，多轮对话的相关性越高，但消耗的 Token 也越多。
     */
    @Schema(description = "携带上下文轮数")
    private Integer sessionRound;

}
