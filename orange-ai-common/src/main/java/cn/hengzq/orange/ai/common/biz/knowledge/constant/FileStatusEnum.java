package cn.hengzq.orange.ai.common.biz.knowledge.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 对话类型
 *
 * @author hengzq
 */
@Getter
public enum FileStatusEnum implements BaseEnum<String> {

    PARSING("文件解析中"),

    PARSE_FAILED("文件解析失败"),

    EMB_PENDING("待向量化"),

    EMB_PROCESSING("向量化中"),

    EMB_FAILED("向量化失败"),

    SUCCESS("成功"),

    ;

    private final String description;


    FileStatusEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
