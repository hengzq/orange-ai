package cn.hengzq.orange.ai.common.biz.workflow.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 应用版本状态
 *
 * @author hengzq
 */
@Getter
public enum WorkflowNodeTypeEnum implements BaseEnum<String> {


    START("开始"),

    END("结束"),

    LLM("大模型"),

    ;

    private final String description;



    WorkflowNodeTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @JsonCreator
    public static WorkflowNodeTypeEnum forValue(String value) {
        return value == null ? null : WorkflowNodeTypeEnum.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
