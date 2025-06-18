package cn.hengzq.orange.ai.core.biz.session.service.impl;

import cn.hengzq.orange.ai.core.biz.session.mapper.SessionModelMapper;
import cn.hengzq.orange.ai.core.biz.session.service.SessionModelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class SessionModelServiceImpl implements SessionModelService {

    private final SessionModelMapper sessionModelMapper;

}
