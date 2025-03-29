package cn.hengzq.orange.ai.common.biz.knowledge.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "根据URL获取网页内容并转换为Markdown - 请求参数")
public class UrlParam implements Serializable {

    @Schema(description = "请求URL")
    private String url;


}
