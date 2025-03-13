package cn.hengzq.orange.ai.common.biz.session.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 会话来源
 */
@Getter
public enum SessionSourceEnum implements BaseEnum<String> {

    AGENT("智能体"),

    AGENT_DEBUG("智能体调试"),

    CHAT_EXPERIENCE("体验中心-聊天会话"),

    ;

    private final String description;


    SessionSourceEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
