package cn.hengzq.orange.ai.model.zhipu.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 向量模型
 */
public enum EmbeddingModelEnum {
    EMBEDDING_3("Embedding-3"),
    EMBEDDING_2("Embedding-2"),

    ;

    public final String model;

    private EmbeddingModelEnum(String model) {
        this.model = model;
    }

    public String getModel() {
        return this.model;
    }

    /**
     * 获取模型列表。
     *
     * @return 返回一个包含所有模型的字符串列表。
     */
    public static List<String> getModelList() {
        return Arrays.stream(values()).map(EmbeddingModelEnum::getModel).toList();
    }
}