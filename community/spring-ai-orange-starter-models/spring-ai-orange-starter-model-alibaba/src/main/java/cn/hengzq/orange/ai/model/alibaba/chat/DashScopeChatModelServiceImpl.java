package cn.hengzq.orange.ai.model.alibaba.chat;

import cn.hengzq.orange.ai.common.biz.chat.dto.ChatModelOptions;
import cn.hengzq.orange.ai.common.biz.chat.service.AbstractChatModelService;
import cn.hengzq.orange.ai.common.biz.model.constant.ModelConstant;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.model.alibaba.constant.ChatModelEnum;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class DashScopeChatModelServiceImpl extends AbstractChatModelService {


    /**
     * 获取当前ChatMode所属平台类型。
     *
     * @return 返回枚举类型的平台，此函数固定返回ALI_BAI_LIAN。
     */
    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.ALI_BAI_LIAN;
    }

    @Override
    protected ChatModel createChatModel(String model, String baseUrl, String apiKey) {
        apiKey = SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).decryptStr(apiKey);
        DashScopeApi dashScopeApi = DashScopeApi.builder().apiKey(apiKey).build();
        return DashScopeChatModel.builder()
                .dashScopeApi(dashScopeApi)
                .defaultOptions(DashScopeChatOptions.builder()
                        .withModel(model)
                        .build())
                .build();
    }

    @Override
    protected ChatOptions createChatOptions(ChatModelOptions options) {
        return DashScopeChatOptions.builder()
                .withModel(options.getModel())
                .withTemperature(options.getTemperature())
                .build();
    }


    @Override
    public List<String> listModel() {
        return ChatModelEnum.getModelList();
    }
}
