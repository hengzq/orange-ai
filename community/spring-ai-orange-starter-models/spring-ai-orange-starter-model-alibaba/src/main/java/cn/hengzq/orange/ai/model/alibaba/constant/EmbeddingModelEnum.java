package cn.hengzq.orange.ai.model.alibaba.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 向量模型
 */
public enum EmbeddingModelEnum {
    EMBEDDING_V1("text-embedding-v1"),
    EMBEDDING_V2("text-embedding-v2"),
    EMBEDDING_V3("text-embedding-v3");

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