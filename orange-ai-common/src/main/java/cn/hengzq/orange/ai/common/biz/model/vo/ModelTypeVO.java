package cn.hengzq.orange.ai.common.biz.model.vo;

import cn.hengzq.orange.ai.common.constant.ModelTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "模型种类 VO")
public class ModelTypeVO implements Serializable {

    public ModelTypeVO() {
    }

    public ModelTypeVO(String name, ModelTypeEnum code) {
        this.name = name;
        this.code = code;
    }

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "模型编码")
    private ModelTypeEnum code;
}
