package cn.hengzq.orange.ai.core.biz.vectorstore.service;

import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VectorStoreServiceFactory {

    private final Map<VectorDatabaseEnum, VectorStoreService> vectorStoreServiceMap;


    public VectorStoreServiceFactory(List<VectorStoreService> vectorStoreServices) {
        this.vectorStoreServiceMap = new HashMap<>(vectorStoreServices.size());
        vectorStoreServices.forEach(chatModelService -> this.vectorStoreServiceMap.put(chatModelService.getVectorDatabaseType(), chatModelService));
    }

    public VectorStoreService getVectorStoreService(VectorDatabaseEnum databaseEnum) {
        return this.vectorStoreServiceMap.get(databaseEnum);
    }
}
