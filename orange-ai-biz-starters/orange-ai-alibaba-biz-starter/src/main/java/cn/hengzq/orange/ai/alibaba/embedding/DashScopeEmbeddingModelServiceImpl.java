package cn.hengzq.orange.ai.alibaba.embedding;

import cn.hengzq.orange.ai.alibaba.constant.EmbeddingModelEnum;
import cn.hengzq.orange.ai.common.biz.embedding.service.AbstractEmbeddingModelService;
import cn.hengzq.orange.ai.common.biz.model.constant.ModelConstant;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class DashScopeEmbeddingModelServiceImpl extends AbstractEmbeddingModelService {

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.ALI_BAI_LIAN;
    }

    @Override
    protected EmbeddingModel createEmbeddingModel(String model, String apiKey) {
        String decryptApiKey = SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).decryptStr(apiKey);
        DashScopeApi dashScopeApi = new DashScopeApi(decryptApiKey);
        return new DashScopeEmbeddingModel(dashScopeApi,
                MetadataMode.EMBED,
                DashScopeEmbeddingOptions.builder()
                        .withModel(model)
                        .withTextType(DashScopeApi.EmbeddingTextType.DOCUMENT.getValue())
                        .build());
    }

    @Override
    public List<String> listModel() {
        return EmbeddingModelEnum.getModelList();
    }

    @Override
    public List<float[]> embed(List<String> texts) {
        return null;
    }
}
