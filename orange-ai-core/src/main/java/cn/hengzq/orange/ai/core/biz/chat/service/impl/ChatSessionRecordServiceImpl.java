package cn.hengzq.orange.ai.core.biz.chat.service.impl;

import cn.hengzq.orange.ai.common.biz.chat.vo.param.ChatSessionRecordRateParam;
import cn.hengzq.orange.ai.core.biz.chat.converter.ChatSessionRecordConverter;
import cn.hengzq.orange.ai.core.biz.chat.entity.ChatSessionRecordEntity;
import cn.hengzq.orange.ai.core.biz.chat.mapper.ChatSessionRecordMapper;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatSessionRecordService;
import cn.hengzq.orange.ai.common.biz.chat.vo.ChatSessionRecordVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.AddChatSessionRecordParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ChatSessionLogListParam;
import cn.hengzq.orange.common.constant.GlobalErrorCodeConstant;
import cn.hengzq.orange.common.exception.ServiceException;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        return ChatSessionRecordConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public Long add(AddChatSessionRecordParam param) {
        ChatSessionRecordEntity entity = ChatSessionRecordConverter.INSTANCE.toEntity(param);
        return chatSessionRecordMapper.insertOne(entity);
    }

    @Override
    public Boolean rateById(Long id, ChatSessionRecordRateParam param) {
        ChatSessionRecordEntity entity = chatSessionRecordMapper.selectById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException(GlobalErrorCodeConstant.GLOBAL_PARAMETER_ID_IS_INVALID);
        }
        entity.setRating(param.getRating());
        return chatSessionRecordMapper.updateOneById(entity);
    }
}
