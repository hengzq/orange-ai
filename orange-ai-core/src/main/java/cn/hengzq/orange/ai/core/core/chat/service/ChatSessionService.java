package cn.hengzq.orange.ai.core.core.chat.service;

import cn.hengzq.orange.ai.common.vo.chat.ChatSessionVO;
import cn.hengzq.orange.ai.common.vo.chat.param.AddChatSessionParam;
import cn.hengzq.orange.ai.common.vo.chat.param.ChatSessionListParam;
import cn.hengzq.orange.ai.common.vo.chat.param.ChatSessionPageParam;
import cn.hengzq.orange.ai.common.vo.chat.param.UpdateChatSessionParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;

public interface ChatSessionService {

    Long add(AddChatSessionParam param);

    Boolean removeById(Long id);

    Boolean updateById(Long id, UpdateChatSessionParam param);

    ChatSessionVO getById(Long id);

    PageDTO<ChatSessionVO> page(ChatSessionPageParam param);

    List<ChatSessionVO> list(ChatSessionListParam param);
}
