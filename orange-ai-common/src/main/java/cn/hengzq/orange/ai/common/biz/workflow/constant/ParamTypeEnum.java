package cn.hengzq.orange.ai.common.biz.workflow.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 参数类型枚举
 *
 * @author hengzq
 */
@Getter
public enum ParamTypeEnum implements BaseEnum<String> {

    REF("引用"),

    STRING("字符串"),

    INTEGER("数字"),

    BOOLEAN("布尔值"),

    OBJECT("Object"),
    ;

    private final String description;


    ParamTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @JsonCreator
    public static ParamTypeEnum forValue(String value) {
        return value == null ? null : ParamTypeEnum.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
