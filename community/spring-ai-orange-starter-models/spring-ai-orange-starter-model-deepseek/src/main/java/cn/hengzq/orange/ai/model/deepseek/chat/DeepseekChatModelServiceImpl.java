package cn.hengzq.orange.ai.model.deepseek.chat;

import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelConversationParam;
import cn.hengzq.orange.ai.common.biz.chat.service.AbstractChatModelService;
import cn.hengzq.orange.ai.common.biz.model.constant.ModelConstant;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
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

    /**
     * 创建聊天模型的实现方法
     * 该方法根据提供的模型信息创建并配置一个ChatModel实例
     * 主要涉及模型的API密钥解密和模型选项的设置
     *
     * @param model 包含模型基本信息的ModelVO对象，用于创建ChatModel
     * @return 返回一个配置好的ChatModel实例
     */
    @Override
    protected ChatModel createChatModel(ModelVO model) {
        String apiKey = SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).decryptStr(model.getApiKey());
        return DeepSeekChatModel.builder()
                .deepSeekApi(DeepSeekApi.builder().apiKey(apiKey).build())
                .defaultOptions(DeepSeekChatOptions.builder()
                        .model(model.getModelName())
                        .build())
                .build();

    }

    @Override
    protected ChatOptions createChatOptions(ChatModelConversationParam param) {
        return DeepSeekChatOptions.builder()
                .model(param.getModel().getModelName())
                .build();
    }


    @Override
    public List<String> listModel() {
        return ChatModelEnum.getModelList();
    }
}
