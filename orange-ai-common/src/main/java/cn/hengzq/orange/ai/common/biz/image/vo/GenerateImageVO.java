package cn.hengzq.orange.ai.common.biz.image.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Schema(description = "生成图片 VO")
public class GenerateImageVO implements Serializable {


    @Schema(description = "图片地址")
    private List<String> urls;


}
