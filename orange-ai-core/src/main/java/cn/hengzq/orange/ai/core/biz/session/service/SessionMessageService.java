package cn.hengzq.orange.ai.core.biz.session.service;

import cn.hengzq.orange.ai.common.biz.session.vo.SessionMessageVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionMessageParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.MessagePageParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionMessageListParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionMessageRateParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;

public interface SessionMessageService {

    List<SessionMessageVO> list(SessionMessageListParam param);

    PageDTO<SessionMessageVO> page(MessagePageParam param);

    String add(AddSessionMessageParam param);

    Boolean rateById(String id, SessionMessageRateParam param);
}
