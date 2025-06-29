package cn.hengzq.orange.ai.model.minimax.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ChatModelEnum {

    MINIMAX_M1("MiniMax-M1"),

    MINIMAX_TEXT_01("MiniMax-Text-01"),
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