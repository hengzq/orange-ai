package cn.hengzq.orange.ai.common.biz.knowledge.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 文档状态
 *
 * @author hengzq
 */
@Getter
public enum DocStatusEnum implements BaseEnum<String> {

    PARSING("文件解析中"),

    PARSE_FAILED("文件解析失败"),

    EMB_PENDING("待向量化"),

    EMB_PROCESSING("向量化中"),

    EMB_FAILED("向量化失败"),

    EMB_COMPLETED("向量化完成"),
    ;

    private final String description;


    DocStatusEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
