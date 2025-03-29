package cn.hengzq.orange.ai.common.biz.session.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 会话类型
 */
@Getter
public enum SessionTypeEnum implements BaseEnum<String> {

    AGENT("智能体"),

    AGENT_DEBUG("智能体调试"),

    CHAT_EXPERIENCE("体验中心-聊天会话"),

    ;

    private final String description;


    SessionTypeEnum(String description) {
        this.description = description;
    }

    public static List<SessionTypeEnum> sessionTypeList() {
        return Arrays.stream(values()).toList();
    }

    public static boolean include(SessionTypeEnum sessionType) {
        return Arrays.stream(values()).anyMatch(item -> item == sessionType);
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
