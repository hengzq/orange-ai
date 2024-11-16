package cn.hengzq.orange.ai.core.biz.chat.service;

import cn.hengzq.orange.ai.common.biz.chat.vo.ChatSessionRecordVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.AddChatSessionRecordParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ChatSessionLogListParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ChatSessionRecordRateParam;

import java.util.List;

public interface ChatSessionRecordService {

    List<ChatSessionRecordVO> list(ChatSessionLogListParam param);

    Long add(AddChatSessionRecordParam param);

    Boolean rateById(Long id, ChatSessionRecordRateParam param);
}
