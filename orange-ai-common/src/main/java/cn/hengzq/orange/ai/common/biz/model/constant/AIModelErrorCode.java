package cn.hengzq.orange.ai.common.biz.model.constant;

import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import cn.hengzq.orange.common.exception.ErrorCode;

public interface AIModelErrorCode extends GlobalErrorCodeConstant {

    String PREFIX = "AIModelErrorCode.";


    String MODEL_DATA_NOT_EXIST_CODE = PREFIX + "0001";
    ErrorCode MODEL_DATA_NOT_EXIST = new ErrorCode(MODEL_DATA_NOT_EXIST_CODE, "模型不存在,请选择正确的模型。");

    String MODEL_PLATFORM_CANNOT_NULL_KEY = PREFIX + "0002";
    ErrorCode MODEL_PLATFORM_CANNOT_NULL = new ErrorCode(MODEL_PLATFORM_CANNOT_NULL_KEY, "模型供应商不能为空。");


    String MODEL_PARAM_APIKEY_OR_BASEURL_IS_ERROR_KEY = PREFIX + "0003";
    ErrorCode MODEL_PARAM_APIKEY_OR_BASEURL_IS_ERROR = new ErrorCode(MODEL_PARAM_APIKEY_OR_BASEURL_IS_ERROR_KEY, "验证失败，请检查参数 baseUrl 或 apiKey 是否正确。");
}
