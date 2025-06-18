package cn.hengzq.orange.ai.core.biz.session.service.impl;

import cn.hengzq.orange.ai.common.biz.session.vo.SessionMessageVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionMessageParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionMessageListParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionMessageRateParam;
import cn.hengzq.orange.ai.core.biz.session.converter.SessionMessageConverter;
import cn.hengzq.orange.ai.core.biz.session.entity.SessionMessageEntity;
import cn.hengzq.orange.ai.core.biz.session.mapper.SessionMessageMapper;
import cn.hengzq.orange.ai.core.biz.session.service.SessionMessageService;
import cn.hengzq.orange.common.constant.GlobalConstant;
import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import cn.hengzq.orange.common.exception.ServiceException;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class SessionMessageServiceImpl implements SessionMessageService {

    private final SessionMessageMapper sessionMessageMapper;

    @Override
    public List<SessionMessageVO> list(SessionMessageListParam param) {
        List<SessionMessageEntity> entityList = sessionMessageMapper.selectList(
                CommonWrappers.<SessionMessageEntity>lambdaQuery()
                        .eqIfPresent(SessionMessageEntity::getSessionId, param.getSessionId())
                        .orderByAsc(SessionMessageEntity::getCreatedAt)
        );
        return SessionMessageConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public String add(AddSessionMessageParam param) {
        SessionMessageEntity entity = SessionMessageConverter.INSTANCE.toEntity(param);
        if (StrUtil.isBlank(entity.getParentId())) {
            entity.setParentId(GlobalConstant.DEFAULT_PARENT_ID);
        }
        return sessionMessageMapper.insertOne(entity);
    }

    @Override
    public Boolean rateById(String id, SessionMessageRateParam param) {
        SessionMessageEntity entity = sessionMessageMapper.selectById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException(GlobalErrorCodeConstant.GLOBAL_PARAMETER_ID_IS_INVALID);
        }
        return sessionMessageMapper.updateOneById(entity);
    }
}
