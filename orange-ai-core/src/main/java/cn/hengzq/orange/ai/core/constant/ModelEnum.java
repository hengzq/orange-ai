package cn.hengzq.orange.ai.core.constant;

import com.alibaba.cloud.ai.tongyi.chat.TongYiChatModel;
import cn.hengzq.orange.context.ApplicationContextHelper;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;

public enum ModelEnum {

    TONGYI(TongYiChatModel.class),
    OLLAMA(OllamaChatModel.class);

    private final Class<? extends ChatModel> chatModel;


    ModelEnum(Class<? extends ChatModel> chatModel) {
        this.chatModel = chatModel;
    }

    public static ModelEnum getInstanceByName(String name) {
        return valueOf(name);
    }

    public static ChatModel getChatModelByName(String name) {
        ModelEnum instance = getInstanceByName(name);
        return ApplicationContextHelper.getBean(instance.getChatModel());
    }

    public static ChatModel getTongYiChatModel() {
        return ApplicationContextHelper.getBean(TONGYI.getChatModel());
    }

    public static ChatModel getOllamaChatModel() {
        return ApplicationContextHelper.getBean(OLLAMA.getChatModel());
    }


    public Class<? extends ChatModel> getChatModel() {
        return chatModel;
    }
}
