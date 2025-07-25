package cn.hengzq.orange.ai.common.biz.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "应用 - 模型配置")
public class AppModelConfig implements Serializable {


    @Schema(description = "温度")
    private Double temperature;


}
