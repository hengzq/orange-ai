package cn.hengzq.orange.ai.alibaba.embedding;

import cn.hengzq.orange.ai.common.biz.embedding.service.EmbeddingModelService;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class DashScopeEmbeddingModelServiceImpl implements EmbeddingModelService {

    private final DashScopeEmbeddingModel dashScopeEmbeddingModel;

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.ALI_BAI_LIAN;
    }

    @Override
    public List<float[]> embed(List<String> texts) {
        return dashScopeEmbeddingModel.embed(texts);
    }
}
