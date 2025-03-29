package cn.hengzq.orange.ai.core.biz.chat.handler.impl;

import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.KnowledgeBaseVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeBaseListParam;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.vectorstore.service.VectorStoreService;
import cn.hengzq.orange.ai.core.biz.chat.handler.AbstractChatHandler;
import cn.hengzq.orange.ai.core.biz.chat.handler.ChatContext;
import cn.hengzq.orange.ai.core.biz.embedding.service.EmbeddingModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.knowledge.service.KnowledgeBaseService;
import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 向量数据库搜索
 */
@Slf4j
@Component
@AllArgsConstructor
public class LoadKnowledgeBaseHandler extends AbstractChatHandler {

    private final KnowledgeBaseService knowledgeBaseService;

    private final EmbeddingModelServiceFactory embeddingModelServiceFactory;

    private final VectorStoreService vectorStoreService;

    @Override
    protected void before(ChatContext context) {

    }

    @Override
    protected void execute(ChatContext context) {
        List<String> baseIds = context.getAgent().getBaseIds();
        if (CollUtil.isEmpty(baseIds)) {
            return;
        }
        List<KnowledgeBaseVO> baseList = knowledgeBaseService.list(KnowledgeBaseListParam.builder().ids(baseIds).build());
        if (CollUtil.isEmpty(baseList)) {
            return;
        }
        List<Advisor> advisors = new ArrayList<>();
        for (KnowledgeBaseVO base : baseList) {
            ModelVO embeddingModel = base.getEmbeddingModel();
            EmbeddingModelService embeddingModelService = embeddingModelServiceFactory.getEmbeddingModelService(embeddingModel.getPlatform());
            VectorStore vectorStore = vectorStoreService.getOrCreateVectorStore(base.getVectorCollectionName(), embeddingModelService.getOrCreateEmbeddingModel(embeddingModel));

            QuestionAnswerAdvisor advisor = new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder()
                    .query(context.getPrompt()).topK(5).build());
            advisors.add(advisor);
        }
        context.setAdvisors(advisors);
    }

    @Override
    protected void after(ChatContext context) {

    }
}
