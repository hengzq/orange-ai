package cn.hengzq.orange.ai.model.alibaba.image;

import cn.hengzq.orange.ai.common.biz.image.service.AbstractImageModelService;
import cn.hengzq.orange.ai.common.biz.model.constant.ModelConstant;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.model.alibaba.constant.ImageModelEnum;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.cloud.ai.dashscope.api.DashScopeImageApi;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageModel;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class DashScopeImageModelServiceImpl extends AbstractImageModelService {

    @Override
    public PlatformEnum getPlatform() {
        return PlatformEnum.ALI_BAI_LIAN;
    }

    @Override
    protected ImageModel createImageModel(ModelVO model) {
        String apiKey = SecureUtil.des(ModelConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).decryptStr(model.getApiKey());
        return new DashScopeImageModel(DashScopeImageApi.builder()
                .apiKey(apiKey)
                .build());
    }

    @Override
    public List<String> listModel() {
        return ImageModelEnum.getModelList();
    }


}
