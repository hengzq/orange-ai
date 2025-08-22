package cn.hengzq.orange.ai.common.biz.workflow.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 应用版本状态
 *
 * @author hengzq
 */
@Getter
public enum WorkflowVersionStatusEnum implements BaseEnum<String> {

    DRAFT("草稿"),

    PUBLISHED("已发布"),


    ;

    private final String description;


    WorkflowVersionStatusEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
