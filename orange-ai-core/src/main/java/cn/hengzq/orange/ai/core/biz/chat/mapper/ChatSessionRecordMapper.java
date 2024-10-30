package cn.hengzq.orange.ai.core.biz.chat.mapper;

import cn.hengzq.orange.ai.core.biz.chat.entity.ChatSessionRecordEntity;
import cn.hengzq.orange.mybatis.mapper.CommonMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @author hengzq
 */
@Repository
public interface ChatSessionRecordMapper extends CommonMapper<ChatSessionRecordEntity> {


    default List<ChatSessionRecordEntity> selectListBySessionId(Long sessionId) {
        if (Objects.isNull(sessionId)) {
            return List.of();
        }
        return selectList(Wrappers.<ChatSessionRecordEntity>lambdaQuery().eq(ChatSessionRecordEntity::getSessionId, sessionId));
    }
}
