package cn.hengzq.orange.ai.common.biz.app.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 对话类型
 *
 * @author hengzq
 */
@Getter
public enum AppTypeEnum implements BaseEnum<String> {

    AGENT("智能体"),
    CHAT_FLOW("对话流"),
    WORK_FLOW("工作流"),

    ;

    private final String description;


    AppTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
