package cn.hengzq.orange.ai.common.biz.chat.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 评价结果
 */
@Getter
public enum RatingEnum implements BaseEnum<String> {

    THUMBS_UP("点赞"),

    THUMBS_DOWN("点踩"),
    ;

    private final String description;


    RatingEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
