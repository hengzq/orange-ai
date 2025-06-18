package cn.hengzq.orange.ai.common.biz.prompt.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;


/**
 * AI 提示词模板类型
 *
 * @author hengzq
 */
@Getter
public enum PromptTemplateEnum implements BaseEnum<String> {

    TEXT_TO_TEXT("文本生成"),

    TEXT_TO_IMAGE("图片生成"),


    ;

    private final String description;

    PromptTemplateEnum(String description) {
        this.description = description;
    }


    @Override
    public String getCode() {
        return this.name();
    }
}
