package cn.hengzq.orange.ai.model.alibaba.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 模型列表：https://bailian.console.aliyun.com/?spm=5176.12818093_47.console-base_product-drawer-right.dsfm.3be92cc9Iblb2e&tab=doc#/doc/?type=model&url=https%3A%2F%2Fhelp.aliyun.com%2Fdocument_detail%2F2840914.html&renderType=iframe
 */
@Getter
public enum ChatModelEnum {
    QWEN3_32B("qwen3-32b"),
    QWEN3_14B("qwen3-14b"),
    QWEN3_8B("qwen3-8b"),

    QWEN_PLUS("qwen-plus"),
    QWEN_TURBO("qwen-turbo"),
    QWEN_MAX("qwen-max"),
    QWEN_MAX_LONGCONTEXT("qwen-max-longcontext"),

    DEEPSEEK_R1("deepseek-r1"),
    ;

    private final String model;

    ChatModelEnum(String model) {
        this.model = model;
    }

    /**
     * 获取模型列表。
     *
     * @return 返回一个包含所有模型的字符串列表。
     */
    public static List<String> getModelList() {
        return Arrays.stream(values()).map(ChatModelEnum::getModel).toList();
    }
}