package cn.hengzq.orange.ai.common.biz.chat.service;

import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import org.springframework.ai.chat.model.ChatModel;

import java.util.List;

public abstract class AbstractChatModelService implements ChatModelService {

    /**
     * ChatModel缓存
     * key：密钥API_KEY
     * value：基于KEY创建的ChatModel
     */
    protected static final Cache<String, ChatModel> chatModelMap = CacheUtil.newLFUCache(100);


    /**
     * 创建ChatModel
     */
    protected abstract ChatModel createChatModel(ModelVO model);


    @Override
    public List<String> listModel() {
        return List.of();
    }

    /**
     * 获取或创建聊天模型。
     *
     * @param model 需要获取或创建的模型对象。
     * @return 如果模型已存在，返回对应的聊天模型；否则，创建新的聊天模型并返回。
     */
    @Override
    public ChatModel getOrCreateChatModel(ModelVO model) {
        if (chatModelMap.containsKey(model.getApiKey())) {
            return chatModelMap.get(model.getApiKey());
        }
        ChatModel chatModel = createChatModel(model);
        chatModelMap.put(model.getApiKey(), chatModel);
        return chatModel;
    }

}
