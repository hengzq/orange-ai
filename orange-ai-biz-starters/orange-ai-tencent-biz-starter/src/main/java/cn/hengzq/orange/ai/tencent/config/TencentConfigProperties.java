package cn.hengzq.orange.ai.tencent.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = TencentConfigProperties.PREFIX)
public class TencentConfigProperties {

    /**
     * 前缀
     */
    public static final String PREFIX = "spring.ai.tencent";

    /**
     * 请求域名
     */
    private String endpoint = "hunyuan.tencentcloudapi.com";

    /**
     * SecretId
     */
    private String secretId;

    /**
     * SecretKey
     */
    private String secretKey;


}
