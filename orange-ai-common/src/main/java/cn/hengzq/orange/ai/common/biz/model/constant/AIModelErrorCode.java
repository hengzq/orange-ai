package cn.hengzq.orange.ai.common.biz.model.constant;

import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import cn.hengzq.orange.common.exception.ErrorCode;

public interface AIModelErrorCode extends GlobalErrorCodeConstant {

    String PREFIX = "AIModelErrorCode.";



    ErrorCode BUTTON_PERMISSION_CANNOT_NULL = new ErrorCode(PREFIX + "0001", "按钮权限编码不能为空");

    ErrorCode BUTTON_PERMISSION_CANNOT_REPEAT = new ErrorCode(PREFIX + "0002", "按钮权限编码重复");

}
