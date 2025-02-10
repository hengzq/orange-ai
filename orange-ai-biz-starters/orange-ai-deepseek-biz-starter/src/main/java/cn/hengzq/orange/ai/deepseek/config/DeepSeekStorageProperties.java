package cn.hengzq.orange.ai.deepseek.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * DeepSeek 配置参数
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = DeepSeekStorageProperties.PREFIX)
public class DeepSeekStorageProperties {

    /**
     * 前缀
     */
    public static final String PREFIX = "orange.ai.deep-seek";

    /**
     * API Keys
     */
    private String token;

    /**
     * 对话接口
     * <a href="https://api-docs.deepseek.com/zh-cn/api/create-chat-completion">...</a>
     */
    private String chatUrl = "https://api.deepseek.com/chat/completions";


}
