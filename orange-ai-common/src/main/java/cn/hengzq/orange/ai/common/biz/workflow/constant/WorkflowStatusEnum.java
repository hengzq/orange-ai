package cn.hengzq.orange.ai.common.biz.workflow.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 对话类型
 *
 * @author hengzq
 */
@Getter
public enum WorkflowStatusEnum implements BaseEnum<String> {

    DRAFT("草稿"),

    PUBLISHED("已发布"),

    PUBLISHED_EDITING("已发布编辑中"),

    ;

    private final String description;


    WorkflowStatusEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
