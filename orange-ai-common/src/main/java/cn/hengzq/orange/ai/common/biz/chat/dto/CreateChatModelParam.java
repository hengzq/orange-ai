package cn.hengzq.orange.ai.common.biz.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatModelParam {

    /**
     * API Key
     */
    private String apiKey;


}
