package cn.hengzq.orange.ai.core.biz.vectorstore.service;

import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class VectorStoreServiceFactory {

    private final Map<VectorDatabaseEnum, Map<PlatformEnum, VectorStoreService>> vectorStoreServiceMap;


    public VectorStoreServiceFactory(List<VectorStoreService> vectorStoreServices) {
        this.vectorStoreServiceMap = new HashMap<>(vectorStoreServices.size());

        for (VectorStoreService item : vectorStoreServices) {
            if (this.vectorStoreServiceMap.containsKey(item.getVectorDatabaseType())) {
                Map<PlatformEnum, VectorStoreService> storeServiceMap = this.vectorStoreServiceMap.get(item.getVectorDatabaseType());
                if (Objects.isNull(storeServiceMap)) {
                    storeServiceMap = new HashMap<>();
                }
                storeServiceMap.put(item.getEmbeddingModelPlatform(), item);
                this.vectorStoreServiceMap.put(item.getVectorDatabaseType(), storeServiceMap);
                continue;
            }
            Map<PlatformEnum, VectorStoreService> storeServiceMap = new HashMap<>();
            storeServiceMap.put(item.getEmbeddingModelPlatform(), item);
            this.vectorStoreServiceMap.put(item.getVectorDatabaseType(), storeServiceMap);
        }

    }

    public VectorStoreService getVectorStoreService(VectorDatabaseEnum databaseEnum, PlatformEnum platformEnum) {
        Map<PlatformEnum, VectorStoreService> storeServiceMap = vectorStoreServiceMap.get(databaseEnum);
        return storeServiceMap.get(platformEnum);
    }
}
