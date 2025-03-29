package cn.hengzq.orange.ai.common.biz.knowledge.vo;

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
@Schema(description = "段落信息")
public class SliceInfo implements Serializable {

    @Schema(description = "段落标题")
    private String title;

    @Schema(description = "段落内容")
    private String content;
}
