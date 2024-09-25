package cn.hengzq.orange.ai.core.biz.chat.converter;


import cn.hengzq.orange.common.converter.Converter;
import cn.hengzq.orange.ai.core.biz.chat.entity.ChatSessionEntity;
import cn.hengzq.orange.ai.common.vo.chat.ChatSessionVO;
import cn.hengzq.orange.ai.common.vo.chat.param.AddChatSessionParam;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import cn.hengzq.orange.ai.common.vo.chat.param.UpdateChatSessionParam;
import cn.hengzq.orange.common.dto.PageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 聊天会话换装器
 *
 * @author hengzq
 */
@Mapper
public interface ChatSessionConverter extends Converter {

    ChatSessionConverter INSTANCE = Mappers.getMapper(ChatSessionConverter.class);

    ChatSessionEntity toEntity(ChatSessionVO vo);

    ChatSessionEntity toEntity(AddChatSessionParam param);

    ChatSessionVO toVO(ChatSessionEntity entity);

    List<ChatSessionVO> toListV0(List<ChatSessionEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "param.name", target = "name")
    ChatSessionEntity toUpdateEntity(ChatSessionEntity entity, UpdateChatSessionParam param);

    PageDTO<ChatSessionVO> toPage(PageDTO<ChatSessionEntity> page);

    @Mapping(source = "param.prompt", target = "name")
    ChatSessionEntity toEntity(ConversationParam param);
}
