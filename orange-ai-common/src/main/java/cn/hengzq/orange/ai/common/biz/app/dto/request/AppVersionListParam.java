package cn.hengzq.orange.ai.common.biz.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Schema(description = "应用版本管理 - 查询所有的数据")
public class AppVersionListParam implements Serializable {

    @Schema(description = "应用名称 模糊查询")
    private String name;

}
