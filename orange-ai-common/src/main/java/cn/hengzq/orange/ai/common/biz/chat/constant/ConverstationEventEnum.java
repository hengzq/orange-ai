package cn.hengzq.orange.ai.common.biz.chat.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 对话事件
 */
@Getter
public enum ConverstationEventEnum implements BaseEnum<String> {

    REPLY("回复"),

    FINISHED("结束"),
    ;

    private final String description;


    ConverstationEventEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
