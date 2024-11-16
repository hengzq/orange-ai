package cn.hengzq.orange.ai.common.biz.platform.vo;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelTypeVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hengzq
 */
@Data
@Schema(description = "平台管理 - VO")
public class PlatformVO implements Serializable {

    public PlatformVO() {
    }

    public PlatformVO(String name, PlatformEnum code) {
        this.name = name;
        this.code = code;
    }

    public PlatformVO(String name, PlatformEnum code, List<ModelTypeVO> modelTypes) {
        this.name = name;
        this.code = code;
        this.modelTypes = modelTypes;
    }

    @Schema(description = "平台名称")
    private String name;

    @Schema(description = "平台编码")
    private PlatformEnum code;

    @Schema(description = "支持的模型种类")
    List<ModelTypeVO> modelTypes;


}
