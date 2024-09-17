package cn.hengzq.orange.ai.common.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

import java.util.List;


/**
 * AI 平台枚举类
 *
 * @author hengzq
 */
@Getter
public enum PlatformEnum implements BaseEnum<String> {

    TONGYI("阿里通义", List.of(ModelTypeEnum.CHAT, ModelTypeEnum.TEXT_TO_IMAGE)),
    OLLAMA("OLLAMA", List.of(ModelTypeEnum.CHAT));


    private final String description;

    private final List<ModelTypeEnum> modelTypes;


    PlatformEnum(String description, List<ModelTypeEnum> modelTypes) {
        this.description = description;
        this.modelTypes = modelTypes;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
