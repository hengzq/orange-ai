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

    ALI_BAI_LIAN("阿里云百炼", List.of(ModelTypeEnum.CHAT, ModelTypeEnum.TEXT_TO_IMAGE)),

    OLLAMA("OLLAMA", List.of(ModelTypeEnum.CHAT)),

    ZHI_PU("智谱AI", List.of(ModelTypeEnum.CHAT)),

    TENCENT("腾讯混元", List.of(ModelTypeEnum.CHAT)),

    QIAN_FAN("百度智能云-千帆ModelBuilder", List.of(ModelTypeEnum.CHAT, ModelTypeEnum.TEXT_TO_IMAGE)),

    ;

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
