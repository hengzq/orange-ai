package cn.hengzq.orange.ai.core.biz.chat.service.impl;

import cn.hengzq.orange.ai.core.biz.chat.converter.ChatSessionConverter;
import cn.hengzq.orange.ai.core.biz.chat.entity.ChatSessionEntity;
import cn.hengzq.orange.ai.core.biz.chat.mapper.ChatSessionMapper;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatSessionService;
import cn.hengzq.orange.ai.common.vo.chat.ChatSessionVO;
import cn.hengzq.orange.ai.common.vo.chat.param.AddChatSessionParam;
import cn.hengzq.orange.ai.common.vo.chat.param.ChatSessionListParam;
import cn.hengzq.orange.ai.common.vo.chat.param.ChatSessionPageParam;
import cn.hengzq.orange.ai.common.vo.chat.param.UpdateChatSessionParam;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.context.GlobalContextHelper;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChatSessionServiceImpl implements ChatSessionService {

    private final ChatSessionMapper chatSessionMapper;

    @Override
    public Long add(AddChatSessionParam param) {
        ChatSessionEntity entity = ChatSessionConverter.INSTANCE.toEntity(param);
        return chatSessionMapper.insertOne(entity);
    }

    @Override
    public Boolean removeById(Long id) {
        return null;
    }

    @Override
    public Boolean updateById(Long id, UpdateChatSessionParam param) {
        ChatSessionEntity entity = chatSessionMapper.selectById(id);
        entity = ChatSessionConverter.INSTANCE.toUpdateEntity(entity, param);
        return chatSessionMapper.updateOneById(entity);
    }

    @Override
    public ChatSessionVO getById(Long id) {
        return ChatSessionConverter.INSTANCE.toVO(chatSessionMapper.selectById(id));
    }

    @Override
    public PageDTO<ChatSessionVO> page(ChatSessionPageParam param) {
        PageDTO<ChatSessionEntity> page = chatSessionMapper.selectPage(param, CommonWrappers.<ChatSessionEntity>lambdaQuery()
                .eqIfPresent(ChatSessionEntity::getUserId, GlobalContextHelper.getUserId())
                .orderByDesc(ChatSessionEntity::getCreatedAt));
        return ChatSessionConverter.INSTANCE.toPage(page);
    }

    @Override
    public List<ChatSessionVO> list(ChatSessionListParam param) {
        List<ChatSessionEntity> entityList = chatSessionMapper.selectList(
                CommonWrappers.<ChatSessionEntity>lambdaQuery()
                        .eqIfPresent(ChatSessionEntity::getUserId, GlobalContextHelper.getUserId())
                        .orderByDesc(ChatSessionEntity::getCreatedAt)
        );
        return ChatSessionConverter.INSTANCE.toListV0(entityList);
    }
}
