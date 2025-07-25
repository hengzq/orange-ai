package cn.hengzq.orange.ai.common.biz.chat.dto;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.prompt.ChatOptions;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatModelOptions implements ChatOptions {

    /**
     * 模型ID 用于统计等查询使用
     */
    private String modelId;

    /**
     * 模型名称
     */
    private String model;

    /**
     * 模型供应商
     */
    private PlatformEnum platform;

    /**
     * 采样温度
     */
    private Double temperature;

    /**
     * 模型地址
     */
    private String baseUrl;

    /**
     * API KEY
     */
    private String apiKey;


    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public Double getFrequencyPenalty() {
        return 0.0;
    }

    @Override
    public Integer getMaxTokens() {
        return 0;
    }

    @Override
    public Double getPresencePenalty() {
        return 0.0;
    }

    @Override
    public List<String> getStopSequences() {
        return List.of();
    }

    @Override
    public Double getTemperature() {
        return this.temperature;
    }

    @Override
    public Integer getTopK() {
        return 0;
    }

    @Override
    public Double getTopP() {
        return 0.0;
    }

    @Override
    public <T extends ChatOptions> T copy() {
        return null;
    }
}
