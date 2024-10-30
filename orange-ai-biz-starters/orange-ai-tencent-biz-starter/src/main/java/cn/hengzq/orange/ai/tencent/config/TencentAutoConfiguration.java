package cn.hengzq.orange.ai.tencent.config;

import cn.hengzq.orange.ai.common.service.chat.ChatModelService;
import cn.hengzq.orange.ai.tencent.chat.HunYuanChatModelServiceImpl;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.hunyuan.v20230901.HunyuanClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(TencentConfigProperties.class)
public class TencentAutoConfiguration {

    private final TencentConfigProperties tencentConfigProperties;

    public TencentAutoConfiguration(TencentConfigProperties tencentConfigProperties) {
        this.tencentConfigProperties = tencentConfigProperties;
        log.info("init {} completed.", this.getClass().getSimpleName());
    }

    @Bean
    public HunyuanClient hunyuanClient() {
        Credential credential = new Credential(tencentConfigProperties.getSecretId(), tencentConfigProperties.getSecretKey());
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(tencentConfigProperties.getEndpoint());
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        return new HunyuanClient(credential, "", clientProfile);
    }

    @Bean
    public ChatModelService hunYuanChatModelService(HunyuanClient hunyuanClient) {
        return new HunYuanChatModelServiceImpl(hunyuanClient);
    }


}
