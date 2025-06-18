package cn.hengzq.orange.ai.model.alibaba.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ChatModelEnum {
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