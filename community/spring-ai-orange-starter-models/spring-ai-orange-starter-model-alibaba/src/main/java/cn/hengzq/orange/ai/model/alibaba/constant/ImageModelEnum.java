package cn.hengzq.orange.ai.model.alibaba.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ImageModelEnum {
    WANX2_1_T2I_PLUS("wanx2.1-t2i-plus"),
    WANX2_1_T2I_TURBO("wanx2.1-t2i-turbo"),
    /**
     * 通义万相文生图 2.0 （文生图V2版）
     * 擅长质感人像，速度中等、成本较低。对应通义万相官网2.0极速模型。
     */
    WANX2_0_T2I_TURBO("wanx2.0-t2i-turbo"),

    STABLE_DIFFUSION_V1_5("stable-diffusion-v1.5"),

    FLUX_SCHNELL("flux-schnell"),
    ;

    private final String model;

    ImageModelEnum(String model) {
        this.model = model;
    }

    /**
     * 获取模型列表。
     *
     * @return 返回一个包含所有模型的字符串列表。
     */
    public static List<String> getModelList() {
        return Arrays.stream(values()).map(ImageModelEnum::getModel).toList();
    }
}