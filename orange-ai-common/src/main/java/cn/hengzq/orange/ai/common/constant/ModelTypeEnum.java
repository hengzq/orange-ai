package cn.hengzq.orange.ai.common.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

/**
 * AI 模型类别管理
 *
 * @author hengzq
 */
@Getter
public enum ModelTypeEnum implements BaseEnum<String> {

    CHAT("聊天模型"),

    TEXT_TO_IMAGE("文生图模型"),

    EMBEDDING("嵌入式模型"),
    ;

    private final String description;


    ModelTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
