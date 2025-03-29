package cn.hengzq.orange.ai.common.biz.model.vo;

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


}
