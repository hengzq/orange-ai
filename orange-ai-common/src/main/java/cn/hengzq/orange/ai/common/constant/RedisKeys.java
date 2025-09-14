package cn.hengzq.orange.ai.common.constant;

public final class RedisKeys {

    private RedisKeys() {
        throw new AssertionError("Cannot be instantiated!");
    }

    public static final String BASE_KEY_PREFIX = "orange-ai:";


    /**
     * 模型 相关缓存KEY
     */
    public static final String MODEL_KEY_PREFIX = BASE_KEY_PREFIX + "model:";
    public static final String MODEL_BASIC_KEY_PREFIX = MODEL_KEY_PREFIX + "basic";
    public static final String MODEL_DETAIL_KEY_PREFIX = MODEL_KEY_PREFIX + "detail";


}