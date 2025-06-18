package cn.hengzq.orange.ai.common.biz.mcp.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 会话类型
 */
@Getter
public enum TransportProtocolEnum implements BaseEnum<String> {

    SSE("SSE"),


    ;

    private final String description;


    TransportProtocolEnum(String description) {
        this.description = description;
    }

    public static List<TransportProtocolEnum> sessionTypeList() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
