package cn.hengzq.orange.ai.common.biz.image.vo.param;

import cn.hengzq.orange.common.dto.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Schema(description = "文生图管理 - 分页查询参数")
public class TextToImagePageParam extends PageParam {


}
