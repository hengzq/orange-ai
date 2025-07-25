package cn.hengzq.orange.ai.model.deepseek.chat;

import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelOptions;
import cn.hengzq.orange.ai.common.biz.chat.service.AbstractChatModelService;
import cn.hengzq.orange.ai.common.biz.model.constant.ModelConstant;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.model.deepseek.constant.ChatModelEnum;
import cn.hutool.crypto.SecureUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.ai.deepseek.api.DeepSeekApi;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class DeepseekChatModelServiceImpl extends AbstractChatModelService {


    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.DEEP_SEEK;
    }

    @Override
    protected ChatModel createChatModel(String model, String baseUrl, String apiKey) {
        apiKey = SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).decryptStr(apiKey);
        return DeepSeekChatModel.builder()
                .deepSeekApi(DeepSeekApi.builder().apiKey(apiKey).build())
                .defaultOptions(DeepSeekChatOptions.builder()
                        .model(model)
                        .build())
                .build();
    }

    @Override
    protected ChatOptions createChatOptions(ChatModelOptions options) {
        return DeepSeekChatOptions.builder()
                .model(options.getModel())
                .build();
    }


    @Override
    public List<String> listModel() {
        return ChatModelEnum.getModelList();
    }
}
