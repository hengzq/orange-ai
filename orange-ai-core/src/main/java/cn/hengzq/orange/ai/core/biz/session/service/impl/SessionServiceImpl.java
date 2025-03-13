package cn.hengzq.orange.ai.core.biz.session.service.impl;

import cn.hengzq.orange.ai.common.biz.session.vo.SessionVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionListParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionPageParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.UpdateSessionParam;
import cn.hengzq.orange.ai.core.biz.session.converter.SessionConverter;
import cn.hengzq.orange.ai.core.biz.session.entity.SessionEntity;
import cn.hengzq.orange.ai.core.biz.session.entity.SessionMessageEntity;
import cn.hengzq.orange.ai.core.biz.session.mapper.SessionMapper;
import cn.hengzq.orange.ai.core.biz.session.mapper.SessionMessageMapper;
import cn.hengzq.orange.ai.core.biz.session.service.SessionService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.context.GlobalContextHelper;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionMapper sessionMapper;

    private final SessionMessageMapper sessionMessageMapper;


    @Override
    public Long add(AddSessionParam param) {
        SessionEntity entity = SessionConverter.INSTANCE.toEntity(param);
        entity.setUserId(GlobalContextHelper.getUserId());
        return sessionMapper.insertOne(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteById(Long id) {
        sessionMessageMapper.delete(CommonWrappers.<SessionMessageEntity>lambdaQuery().eq(SessionMessageEntity::getSessionId, id));
        return sessionMapper.deleteOneById(id);
    }

    @Override
    public Boolean updateById(Long id, UpdateSessionParam param) {
        SessionEntity entity = sessionMapper.selectById(id);
        entity = SessionConverter.INSTANCE.toUpdateEntity(entity, param);
        return sessionMapper.updateOneById(entity);
    }

    @Override
    public SessionVO getById(Long id) {
        return SessionConverter.INSTANCE.toVO(sessionMapper.selectById(id));
    }

    @Override
    public PageDTO<SessionVO> page(SessionPageParam param) {
        PageDTO<SessionEntity> page = sessionMapper.selectPage(param, CommonWrappers.<SessionEntity>lambdaQuery()
                .eqIfPresent(SessionEntity::getSource, param.getSource())
                .eqIfPresent(SessionEntity::getModelId, param.getModelId())
                .eqIfPresent(SessionEntity::getAssociationId, param.getAssociationId())
                .orderByDesc(SessionEntity::getCreatedAt));
        return SessionConverter.INSTANCE.toPage(page);
    }

    @Override
    public List<SessionVO> list(SessionListParam param) {
        List<SessionEntity> entityList = sessionMapper.selectList(
                CommonWrappers.<SessionEntity>lambdaQuery()
                        .eqIfPresent(SessionEntity::getSource, param.getSource())
                        .eqIfPresent(SessionEntity::getModelId, param.getModelId())
                        .eqIfPresent(SessionEntity::getAssociationId, param.getAssociationId())
                        .orderByDesc(SessionEntity::getCreatedAt)
        );
        return SessionConverter.INSTANCE.toListV0(entityList);
    }
}
