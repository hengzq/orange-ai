package cn.hengzq.orange.ai.common.biz.chat.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 对话类型
 *
 * @author hengzq
 */
@Getter
public enum ChatTypeEnum implements BaseEnum<String> {

    AGENT("智能体对话"),

    ;

    private final String description;


    ChatTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
