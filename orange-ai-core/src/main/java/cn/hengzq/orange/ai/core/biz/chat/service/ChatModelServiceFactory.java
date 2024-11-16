package cn.hengzq.orange.ai.core.biz.chat.service;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChatModelServiceFactory {

    private final Map<PlatformEnum, ChatModelService> chatServiceMap;

    public ChatModelServiceFactory(List<ChatModelService> chatModelServiceList) {
        this.chatServiceMap = new HashMap<>(chatModelServiceList.size());
        chatModelServiceList.forEach(chatModelService -> chatServiceMap.put(chatModelService.getPlatform(), chatModelService));
    }

    public ChatModelService getChatModelService(PlatformEnum platform) {
        return chatServiceMap.get(platform);
    }
}
