package cn.hengzq.orange.ai.core.biz.vectorstore.service.impl;

import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import cn.hengzq.orange.ai.common.biz.vectorstore.vo.param.AddVectorDataParam;
import cn.hengzq.orange.ai.common.biz.vectorstore.vo.param.VectorDataListParam;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.core.biz.vectorstore.service.VectorDataService;
import cn.hengzq.orange.ai.core.biz.vectorstore.service.VectorStoreServiceFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class VectorDataServiceImpl implements VectorDataService {

    private final VectorStoreServiceFactory vectorStoreServiceFactory;

    @Override
    public Long add(AddVectorDataParam param) {
        VectorStoreService vectorStoreService = vectorStoreServiceFactory.getVectorStoreService(param.getVectorDatabase(), PlatformEnum.ALI_BAI_LIAN);

        List<String> texts = param.getTexts();
        List<Document> documents = new ArrayList<>(texts.size());
        for (String text : texts) {
            Document document = Document.builder()
                    .text(text)
                    .metadata("xxx", "eee")
                    .build();
            documents.add(document);
        }
        vectorStoreService.add(documents);
        return null;
    }

    @Override
    public List<Document> list(VectorDataListParam param) {
        VectorStoreService vectorStoreService = vectorStoreServiceFactory.getVectorStoreService(param.getVectorDatabase(), PlatformEnum.ALI_BAI_LIAN);
        return vectorStoreService.list(param);
    }

}
