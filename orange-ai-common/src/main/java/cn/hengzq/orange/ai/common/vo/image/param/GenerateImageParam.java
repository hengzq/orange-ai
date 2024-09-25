package cn.hengzq.orange.ai.common.vo.image.param;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "生成图片请求参数")
public class GenerateImageParam implements Serializable {

    @Schema(description = "所属平台")
    private PlatformEnum platform = PlatformEnum.ALI_BAI_LIAN;

    @Schema(description = "模型编码")
    private String modelCode = "wanx-style-cosplay-v1";

    @Schema(description = "生成图片的提示")
    private String prompt;

    @Schema(description = "生成图片数量，默认为：1")
    private Integer quantity = 1;

    @Schema(description = "生成图片宽度，默认：1024")
    private Integer width = 1024;

    @Schema(description = "生成图片高度，默认：1024")
    private Integer height = 1024;
}
