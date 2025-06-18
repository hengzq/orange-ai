package cn.hengzq.orange.ai.common.biz.image.dto;

import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.ai.chat.messages.Message;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class ImageModelGenerateParam {

    /**
     * 提示词
     */
    private String prompt;

    /**
     * 模型
     */
    private ModelVO model;

    @Schema(description = "生成图片数量，默认为：1")
    private Integer quantity = 1;

    @Schema(description = "生成图片宽度，默认：1024")
    private Integer width = 1024;

    @Schema(description = "生成图片高度，默认：1024")
    private Integer height = 1024;

}
