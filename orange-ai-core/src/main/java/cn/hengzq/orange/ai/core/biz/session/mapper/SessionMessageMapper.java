package cn.hengzq.orange.ai.core.biz.session.mapper;

import cn.hengzq.orange.ai.core.biz.session.entity.SessionMessageEntity;
import cn.hengzq.orange.mybatis.mapper.CommonMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @author hengzq
 */
@Repository
public interface SessionMessageMapper extends CommonMapper<SessionMessageEntity> {


    default List<SessionMessageEntity> selectListBySessionId(Long sessionId) {
        if (Objects.isNull(sessionId)) {
            return List.of();
        }
        return selectList(Wrappers.<SessionMessageEntity>lambdaQuery().eq(SessionMessageEntity::getSessionId, sessionId));
    }
}
