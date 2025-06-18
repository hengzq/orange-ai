package cn.hengzq.orange.ai.model.ollama.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ChatModelEnum {

    DEEPSEEK_R1_1_5B("deepseek-r1:1.5b"),
    DEEPSEEK_R1_7B("deepseek-r1:7b"),
    DEEPSEEK_R1_8B("deepseek-r1:8b"),
    DEEPSEEK_R1_14B("deepseek-r1:14b"),
    DEEPSEEK_R1_32B("deepseek-r1:32b"),
    DEEPSEEK_R1_70B("deepseek-r1:70b"),

    QWEN2_5_0_5B("qwen2.5:0.5b"),
    QWEN2_5_1_5B("qwen2.5:1.5b"),
    QWEN2_5_3B("qwen2.5:3b"),
    QWEN2_5_7B("qwen2.5:7b"),
    QWEN2_5_14B("qwen2.5:14b"),
    QWEN2_5_32B("qwen2.5:32b"),

    LLAMA3_8B("llama3:8b"),

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