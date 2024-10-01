package cn.hengzq.orange.ai.core.biz.chat.service.impl;

import cn.hengzq.orange.ai.core.biz.chat.converter.ChatSessionLogConverter;
import cn.hengzq.orange.ai.core.biz.chat.entity.ChatSessionRecordEntity;
import cn.hengzq.orange.ai.core.biz.chat.mapper.ChatSessionRecordMapper;
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

    private final ChatSessionRecordMapper chatSessionRecordMapper;

    @Override
    public List<ChatSessionRecordVO> list(ChatSessionLogListParam param) {
        List<ChatSessionRecordEntity> entityList = chatSessionRecordMapper.selectList(
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
        return chatSessionRecordMapper.insertOne(entity);
    }
}
