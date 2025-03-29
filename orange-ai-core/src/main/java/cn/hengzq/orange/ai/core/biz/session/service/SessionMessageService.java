package cn.hengzq.orange.ai.core.biz.session.service;

import cn.hengzq.orange.ai.common.biz.session.vo.SessionMessageVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionMessageParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionMessageListParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionMessageRateParam;

import java.util.List;

public interface SessionMessageService {

    List<SessionMessageVO> list(SessionMessageListParam param);

    String add(AddSessionMessageParam param);

    Boolean rateById(String id, SessionMessageRateParam param);
}
