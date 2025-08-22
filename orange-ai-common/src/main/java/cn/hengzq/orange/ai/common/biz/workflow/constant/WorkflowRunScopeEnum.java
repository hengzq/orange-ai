package cn.hengzq.orange.ai.common.biz.workflow.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 工作流 运行范围
 *
 * @author hengzq
 */
@Getter
public enum WorkflowRunScopeEnum implements BaseEnum<String> {


    WORKFLOW("整个工作流"),

    NODE("单个节点独立执行"),
    ;

    private final String description;


    WorkflowRunScopeEnum(String description) {
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
