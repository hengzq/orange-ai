package cn.hengzq.orange.ai.common.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 消息类型
 *
 * @author hengzq
 */
@Getter
public enum MessageTypeEnum implements BaseEnum<String> {

    USER("用户消息"),
    /**
     * 这是AI助手生成并返回给用户的消息。它基于对用户消息的理解，可以是答案、建议、查询结果或进一步的询问，旨在帮助用户解决问题或完成任务。
     */
    ASSISTANT("助手消息"),
    /**
     * 这类消息通常由系统本身发送，用于初始化对话环境、设置参数、提供上下文或指示AI助手如何响应用户。系统消息可以包含规则、约束、目标等，帮助AI更好地理解和执行任务
     */
    SYSTEM("系统消息"),

    FUNCTION(""),
    ;

    private final String description;


    MessageTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
