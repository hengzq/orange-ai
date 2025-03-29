package cn.hengzq.orange.ai.alibaba;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModelServiceFactory {

    private final Map<String, DashScopeChatModel> dashScopeChatModelMap = new ConcurrentHashMap<>();

    private final Map<String, DashScopeImageModel> dashScopeImageModelMap = new ConcurrentHashMap<>();


    public static DashScopeChatModel getChatModel() {
        return null;
    }

}