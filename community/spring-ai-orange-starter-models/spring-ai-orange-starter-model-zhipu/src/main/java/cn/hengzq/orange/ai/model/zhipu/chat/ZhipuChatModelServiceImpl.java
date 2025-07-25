package cn.hengzq.orange.ai.model.zhipu.chat;

import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelOptions;
import cn.hengzq.orange.ai.common.biz.chat.service.AbstractChatModelService;
import cn.hengzq.orange.ai.common.biz.model.constant.ModelConstant;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.model.zhipu.constant.ChatModelEnum;
import cn.hutool.crypto.SecureUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class ZhipuChatModelServiceImpl extends AbstractChatModelService {

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.ZHI_PU;
    }

    @Override
    protected ChatModel createChatModel(String model, String baseUrl, String apiKey) {
        apiKey = SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).decryptStr(apiKey);
        ZhiPuAiApi zhiPuAiApi = new ZhiPuAiApi(apiKey);
        return new ZhiPuAiChatModel(zhiPuAiApi, ZhiPuAiChatOptions.builder()
                .model(model)
                .build());
    }

    @Override
    protected ChatOptions createChatOptions(ChatModelOptions options) {
        return ZhiPuAiChatOptions.builder()
                .model(options.getModel())
                .temperature(options.getTemperature())
                .build();
    }

    @Override
    public List<String> listModel() {
        return ChatModelEnum.getModelList();
    }
}
