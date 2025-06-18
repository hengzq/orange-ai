package cn.hengzq.orange.ai.core.biz.session.converter;


import cn.hengzq.orange.ai.common.biz.chat.vo.param.ConversationStreamParam;
import cn.hengzq.orange.ai.common.biz.session.vo.SessionVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.UpdateSessionParam;
import cn.hengzq.orange.ai.core.biz.session.entity.SessionEntity;
import cn.hengzq.orange.common.converter.Converter;
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
public interface SessionConverter extends Converter {

    SessionConverter INSTANCE = Mappers.getMapper(SessionConverter.class);

    SessionEntity toEntity(SessionVO vo);

    SessionEntity toEntity(AddSessionParam param);

    SessionVO toVO(SessionEntity entity);

    List<SessionVO> toListV0(List<SessionEntity> entityList);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "param.name", target = "name")
    SessionEntity toUpdateEntity(SessionEntity entity, UpdateSessionParam param);

    PageDTO<SessionVO> toPage(PageDTO<SessionEntity> page);

    @Mapping(source = "param.prompt", target = "name")
    SessionEntity toEntity(ConversationStreamParam param);
}
