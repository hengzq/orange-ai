package cn.hengzq.orange.ai.core.core.chat.mapper;

import cn.hengzq.orange.ai.core.core.chat.entity.ChatSessionRecordEntity;
import cn.hengzq.orange.mybatis.mapper.CommonMapper;
import org.springframework.stereotype.Repository;

/**
 * @author hengzq
 */
@Repository
public interface ChatSessionLogMapper extends CommonMapper<ChatSessionRecordEntity> {


}