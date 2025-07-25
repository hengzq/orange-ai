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

    ALI_BAI_LIAN("阿里云百炼", 10, List.of(ModelTypeEnum.CHAT, ModelTypeEnum.TEXT_TO_IMAGE, ModelTypeEnum.EMBEDDING)),

    OLLAMA("OLLAMA", 20, List.of(ModelTypeEnum.CHAT, ModelTypeEnum.EMBEDDING)),

    ZHI_PU("智谱AI", 30, List.of(ModelTypeEnum.CHAT)),

//    TENCENT("腾讯混元", 40, List.of(ModelTypeEnum.CHAT)),
//
//    QIAN_FAN("百度智能云-千帆ModelBuilder", 50, List.of(ModelTypeEnum.CHAT, ModelTypeEnum.TEXT_TO_IMAGE)),

    DEEP_SEEK("DeepSeek", 60, List.of(ModelTypeEnum.CHAT)),

    /**
     * <a href="https://www.minimaxi.com/">MiniMax 官网</a>
     */
    MINI_MAX("MiniMax", 70, List.of(ModelTypeEnum.CHAT)),

    ;

    private final String description;

    /**
     * 排序
     */
    private final Integer sort;

    private final List<ModelTypeEnum> modelTypes;


    PlatformEnum(String description, Integer sort, List<ModelTypeEnum> modelTypes) {
        this.description = description;
        this.sort = sort;
        this.modelTypes = modelTypes;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
