package cn.hengzq.orange.ai.common.biz.model.constant;

import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import cn.hengzq.orange.common.exception.ErrorCode;

public interface AIModelErrorCode extends GlobalErrorCodeConstant {

    String PREFIX = "AIModelErrorCode.";


    String MODEL_DATA_NOT_EXIST_CODE = PREFIX + "0001";
    ErrorCode MODEL_DATA_NOT_EXIST = new ErrorCode(MODEL_DATA_NOT_EXIST_CODE, "模型不存在,请选择正确的模型。");

    ErrorCode BUTTON_PERMISSION_CANNOT_REPEAT = new ErrorCode(PREFIX + "0002", "按钮权限编码重复");

    ErrorCode CHAT_MODEL_CREATE_ERROR = new ErrorCode(PREFIX + "0003", "创建对话模型失败");
}
