package cn.hengzq.orange.ai.core.biz.chat.converter;


import cn.hengzq.orange.common.converter.Converter;
import cn.hengzq.orange.ai.core.biz.chat.entity.ChatSessionRecordEntity;
import cn.hengzq.orange.ai.common.vo.chat.ChatSessionRecordVO;
import cn.hengzq.orange.ai.common.vo.chat.param.AddChatSessionRecordParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 聊天会话记录换装器
 *
 * @author hengzq
 */
@Mapper
public interface ChatSessionRecordConverter extends Converter {

    ChatSessionRecordConverter INSTANCE = Mappers.getMapper(ChatSessionRecordConverter.class);

    List<ChatSessionRecordVO> toListVO(List<ChatSessionRecordEntity> entityList);

    ChatSessionRecordEntity toEntity(AddChatSessionRecordParam param);
}
