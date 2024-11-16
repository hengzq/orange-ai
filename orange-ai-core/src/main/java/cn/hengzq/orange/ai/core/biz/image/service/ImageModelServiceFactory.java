package cn.hengzq.orange.ai.core.biz.image.service;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.biz.image.service.ImageModelService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ImageModelServiceFactory {

    private final Map<PlatformEnum, ImageModelService> chatServiceMap;

    public ImageModelServiceFactory(List<ImageModelService> imageModelServiceList) {
        this.chatServiceMap = new HashMap<>(imageModelServiceList.size());
        imageModelServiceList.forEach(textToImageService -> chatServiceMap.put(textToImageService.getPlatform(), textToImageService));
    }

    public ImageModelService getImageModelService(PlatformEnum platform) {
        return chatServiceMap.get(platform);
    }
}
