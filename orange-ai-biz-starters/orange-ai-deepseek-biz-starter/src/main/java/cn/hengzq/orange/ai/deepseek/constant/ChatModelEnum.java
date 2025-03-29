package cn.hengzq.orange.ai.deepseek.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ChatModelEnum {
    DEEPSEEK_V3("deepseek-chat"),

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