package cn.hengzq.orange.ai.common.biz.embedding.service;


import cn.hengzq.orange.ai.common.constant.PlatformEnum;

import java.util.List;

public interface EmbeddingModelService {

    PlatformEnum getPlatform();

    List<float[]> embed(List<String> texts);
}
