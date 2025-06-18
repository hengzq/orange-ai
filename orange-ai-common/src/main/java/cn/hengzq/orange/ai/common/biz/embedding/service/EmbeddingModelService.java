package cn.hengzq.orange.ai.common.biz.embedding.service;


import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;

import java.util.List;

public interface EmbeddingModelService {

    PlatformEnum getPlatform();


    EmbeddingModel getOrCreateEmbeddingModel(ModelVO model);

    /**
     * 模型列表
     */
    List<String> listModel();


    List<float[]> embed(List<String> texts);
}
