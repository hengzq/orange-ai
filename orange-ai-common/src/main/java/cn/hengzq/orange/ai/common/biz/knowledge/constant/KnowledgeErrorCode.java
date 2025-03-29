package cn.hengzq.orange.ai.common.biz.knowledge.constant;

import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import cn.hengzq.orange.common.exception.ErrorCode;

public interface KnowledgeErrorCode extends GlobalErrorCodeConstant {

    String PREFIX = "AIKnowledgeErrorCode.";


    String KNOWLEDGE_BASE_ID_CANNOT_NULL_KEY = PREFIX + "0001";
    ErrorCode KNOWLEDGE_BASE_ID_CANNOT_NULL = new ErrorCode(KNOWLEDGE_BASE_ID_CANNOT_NULL_KEY, "知识库ID不能为空");

    String KNOWLEDGE_BASE_DOES_NOT_EXIST_KEY = PREFIX + "0002";
    ErrorCode KNOWLEDGE_BASE_DOES_NOT_EXIST = new ErrorCode(KNOWLEDGE_BASE_DOES_NOT_EXIST_KEY, "知识库不存在，请检查参数。");

    String  KNOWLEDGE_BASE_DELETE_FAILED_KEY = PREFIX + "0003";
    ErrorCode KNOWLEDGE_BASE_DELETE_FAILED = new ErrorCode(KNOWLEDGE_BASE_DELETE_FAILED_KEY, "知识库已被使用，请先删除关联的智能体！");

    String DOC_ID_CANNOT_NULL_KEY = PREFIX + "1000";
    ErrorCode DOC_ID_CANNOT_NULL = new ErrorCode(DOC_ID_CANNOT_NULL_KEY, "文档ID不能为空");

    String DOC_DELETE_FAILED_KEY = PREFIX + "1001";
    ErrorCode DOC_DELETE_FAILED = new ErrorCode(DOC_DELETE_FAILED_KEY, "文档删除失败.");
}
