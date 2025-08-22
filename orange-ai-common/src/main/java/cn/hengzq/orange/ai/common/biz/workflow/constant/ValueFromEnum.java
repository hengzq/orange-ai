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
public enum ValueFromEnum implements BaseEnum<String> {

    REF("引用其他节点的变量"),

    INPUT("用户输入"),

    ;

    private final String description;


    ValueFromEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @JsonCreator
    public static WorkflowRunScopeEnum forValue(String value) {
        return value == null ? null : WorkflowRunScopeEnum.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }

}
