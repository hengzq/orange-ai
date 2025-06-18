package cn.hengzq.orange.ai.model.zhipu.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ChatModelEnum {

    GLM_ZERO_PREVIEW("GLM-Zero-Preview"),
    GLM_4_PLUS("GLM-4-Plus"),
    GLM_4_AIR("GLM-4-Air"),
    GLM_4_LONG("GLM-4-Long"),

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