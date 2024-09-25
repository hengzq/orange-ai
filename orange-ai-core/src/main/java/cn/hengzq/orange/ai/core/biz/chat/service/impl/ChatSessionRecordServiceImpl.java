package cn.hengzq.orange.ai.core.biz.chat.service.impl;

import cn.hengzq.orange.ai.core.biz.chat.converter.ChatSessionLogConverter;
import cn.hengzq.orange.ai.core.biz.chat.entity.ChatSessionRecordEntity;
import cn.hengzq.orange.ai.core.biz.chat.mapper.ChatSessionLogMapper;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatSessionRecordService;
import cn.hengzq.orange.ai.common.vo.chat.ChatSessionRecordVO;
import cn.hengzq.orange.ai.common.vo.chat.param.AddChatSessionRecordParam;
import cn.hengzq.orange.ai.common.vo.chat.param.ChatSessionLogListParam;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ChatSessionRecordServiceImpl implements ChatSessionRecordService {

    private final ChatSessionLogMapper chatSessionLogMapper;

    @Override
    public List<ChatSessionRecordVO> list(ChatSessionLogListParam param) {
        List<ChatSessionRecordEntity> entityList = chatSessionLogMapper.selectList(
                CommonWrappers.<ChatSessionRecordEntity>lambdaQuery()
                        .eqIfPresent(ChatSessionRecordEntity::getUserId, param.getUserId())
                        .eqIfPresent(ChatSessionRecordEntity::getSessionId, param.getSessionId())
                        .orderByAsc(ChatSessionRecordEntity::getCreatedAt)
        );
        return ChatSessionLogConverter.INSTANCE.toListV0(entityList);
    }

    @Override
    public Long add(AddChatSessionRecordParam param) {
        ChatSessionRecordEntity entity = ChatSessionLogConverter.INSTANCE.toEntity(param);
        entity.setCreatedBy(-100L);
        return chatSessionLogMapper.insertOne(entity);
    }
}
