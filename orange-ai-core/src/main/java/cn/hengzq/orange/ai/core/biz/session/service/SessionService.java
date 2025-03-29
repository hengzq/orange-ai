package cn.hengzq.orange.ai.core.biz.session.service;

import cn.hengzq.orange.ai.common.biz.session.vo.SessionVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionListParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionPageParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.UpdateSessionParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;

public interface SessionService {

    String add(AddSessionParam param);

    Boolean deleteById(String id);

    Boolean updateById(String id, UpdateSessionParam param);

    SessionVO getById(String id);

    PageDTO<SessionVO> page(SessionPageParam param);

    List<SessionVO> list(SessionListParam param);
}
