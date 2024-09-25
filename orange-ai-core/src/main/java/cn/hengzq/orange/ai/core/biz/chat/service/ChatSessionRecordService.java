package cn.hengzq.orange.ai.core.biz.chat.service;

import cn.hengzq.orange.ai.common.vo.chat.ChatSessionRecordVO;
import cn.hengzq.orange.ai.common.vo.chat.param.AddChatSessionRecordParam;
import cn.hengzq.orange.ai.common.vo.chat.param.ChatSessionLogListParam;

import java.util.List;

public interface ChatSessionRecordService {

    List<ChatSessionRecordVO> list(ChatSessionLogListParam param);

    Long add(AddChatSessionRecordParam param);

}
