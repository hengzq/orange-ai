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
public enum WorkflowRunStatusEnum implements BaseEnum<String> {


    PENDING("待执行"),

    RUNNING("正在执行"),

    SUCCEEDED("执行成功"),

    FAILED("执行失败"),

    CANCELLED("被用户或系统取消");

    private final String description;


    WorkflowRunStatusEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @JsonCreator
    public static WorkflowRunStatusEnum forValue(String value) {
        return value == null ? null : WorkflowRunStatusEnum.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
