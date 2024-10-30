package cn.hengzq.orange.ai.common.exception;

import cn.hengzq.orange.common.exception.ErrorCode;

public interface AIChatErrorCode {
    String PREFIX = "AIChatErrorCode.";


    String CHAT_NON_STREAM_RESPONSE_ERROR_KEY = PREFIX + ".0001";
    ErrorCode CHAT_NON_STREAM_RESPONSE_ERROR = new ErrorCode(CHAT_NON_STREAM_RESPONSE_ERROR_KEY, "期望一个流，但收到一个非流响应。");


    String CHAT_TENCENT_CALL_ERROR_KEY = PREFIX + ".1100";
    ErrorCode CHAT_TENCENT_CALL_ERROR = new ErrorCode(CHAT_TENCENT_CALL_ERROR_KEY, "调用腾讯聊天大模型异常。");

}
