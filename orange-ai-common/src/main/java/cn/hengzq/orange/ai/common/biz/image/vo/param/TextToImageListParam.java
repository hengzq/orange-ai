package cn.hengzq.orange.ai.common.biz.image.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@Schema(description = "文生图管理 - 列表查询参数")
public class TextToImageListParam implements Serializable {

}
