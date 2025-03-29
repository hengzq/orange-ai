package cn.hengzq.orange.ai.common.biz.knowledge.constant;


import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import cn.hengzq.orange.common.exception.ServiceException;
import lombok.Getter;

/**
 * 文档切片向量化状态
 */
@Getter
public enum SliceEmbStatus {

    PENDING("待向量化"),
    PROCESSING("向量化中"),
    COMPLETED("向量化完成"),
    FAILED("向量化失败");

    private final String description;

    SliceEmbStatus(String description) {
        this.description = description;
    }

    public static SliceEmbStatus fromName(String name) {
        for (SliceEmbStatus status : values()) {
            if (status.name().equalsIgnoreCase(name)) {
                return status;
            }
        }
        throw new ServiceException(GlobalErrorCodeConstant.GLOBAL_PARAMETER_IS_INVALID);
    }
}
