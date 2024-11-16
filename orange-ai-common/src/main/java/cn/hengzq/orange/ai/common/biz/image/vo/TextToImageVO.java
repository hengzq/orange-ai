package cn.hengzq.orange.ai.common.biz.image.vo;

import cn.hengzq.orange.common.dto.BaseTenantDTO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "生成图片 VO")
public class TextToImageVO extends BaseTenantDTO {

    @Schema(description = "主键", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "模型所属平台")
    private PlatformEnum platform;

    @Schema(description = "模型编码")
    private String modelCode;

    @Schema(description = "提示词")
    private String prompt;

    @Schema(description = "生成图片数量")
    private Integer quantity;

    @Schema(description = "生成图片宽度")
    private Integer width;

    @Schema(description = "生成图片高度")
    private Integer height;

    @Schema(description = "图片地址")
    private List<String> urls;

}
