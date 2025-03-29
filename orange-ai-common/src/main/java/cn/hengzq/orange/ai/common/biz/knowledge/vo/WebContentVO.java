package cn.hengzq.orange.ai.common.biz.knowledge.vo;

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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Web 网页内容")
public class WebContentVO implements Serializable {

    @Schema(description = "网站标题")
    private String title;

    @Schema(description = "网站内容")
    private String content;
}
