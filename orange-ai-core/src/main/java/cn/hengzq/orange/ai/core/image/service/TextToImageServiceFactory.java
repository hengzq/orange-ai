package cn.hengzq.orange.ai.core.image.service;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TextToImageServiceFactory {

    private final Map<PlatformEnum, ImageModelService> chatServiceMap;

    public TextToImageServiceFactory(List<ImageModelService> textToImageServiceList) {
        this.chatServiceMap = new HashMap<>(textToImageServiceList.size());
        textToImageServiceList.forEach(textToImageService -> chatServiceMap.put(textToImageService.getPlatform(), textToImageService));
    }

    public ImageModelService getChatService(PlatformEnum platform) {
        return chatServiceMap.get(platform);
    }
}
