package cn.hengzq.orange.ai.model.minimax.chat;

import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelOptions;
import cn.hengzq.orange.ai.common.biz.chat.service.AbstractChatModelService;
import cn.hengzq.orange.ai.common.biz.model.constant.ModelConstant;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.model.minimax.constant.ChatModelEnum;
import cn.hutool.crypto.SecureUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.minimax.MiniMaxChatModel;
import org.springframework.ai.minimax.MiniMaxChatOptions;
import org.springframework.ai.minimax.api.MiniMaxApi;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class MiniMaxChatModelServiceImpl extends AbstractChatModelService {

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.MINI_MAX;
    }

    @Override
    protected ChatModel createChatModel(ModelVO model) {
        String apiKey = SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).decryptStr(model.getApiKey());
        MiniMaxApi miniMaxApi = new MiniMaxApi(apiKey);
        return new MiniMaxChatModel(miniMaxApi, MiniMaxChatOptions.builder()
                .model(model.getModelName())
                .build());
    }

    @Override
    protected ChatModel createChatModel(String model, String baseUrl, String apiKey) {
        apiKey = SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).decryptStr(apiKey);
        MiniMaxApi miniMaxApi = new MiniMaxApi(apiKey);
        return new MiniMaxChatModel(miniMaxApi, MiniMaxChatOptions.builder()
                .model(model)
                .build());
    }

    @Override
    protected ChatOptions createChatOptions(ChatModelOptions options) {
        return MiniMaxChatOptions.builder()
                .model(options.getModel())
                .temperature(options.getTemperature())
                .build();
    }

    @Override
    public List<String> listModel() {
        return ChatModelEnum.getModelList();
    }
}
