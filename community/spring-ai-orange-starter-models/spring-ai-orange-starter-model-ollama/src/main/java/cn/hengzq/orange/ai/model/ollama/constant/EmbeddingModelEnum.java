package cn.hengzq.orange.ai.model.ollama.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 向量模型
 */
public enum EmbeddingModelEnum {
    NOMIC_EMBED_TEXT_137M_V1_5_FP16("nomic-embed-text:137m-v1.5-fp16"),
    MXBAI_EMBED_LARGE_335M("mxbai-embed-large:335m"),
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