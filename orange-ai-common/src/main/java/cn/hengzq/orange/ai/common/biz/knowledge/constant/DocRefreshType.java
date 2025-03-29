package cn.hengzq.orange.ai.common.biz.knowledge.constant;


import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import cn.hengzq.orange.common.exception.ServiceException;
import lombok.Getter;

/**
 * 文档刷新类型
 */
@Getter
public enum DocRefreshType {

    ADD("新增刷新"),
    FORCED("强制刷新,会先删除已有的向量，从新向量"),
    ;

    private final String description;

    DocRefreshType(String description) {
        this.description = description;
    }

    public static DocRefreshType fromName(String name) {
        for (DocRefreshType status : values()) {
            if (status.name().equalsIgnoreCase(name)) {
                return status;
            }
        }
        throw new ServiceException(GlobalErrorCodeConstant.GLOBAL_PARAMETER_IS_INVALID);
    }
}
