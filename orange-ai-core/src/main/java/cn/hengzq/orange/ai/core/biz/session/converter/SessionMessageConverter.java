package cn.hengzq.orange.ai.core.biz.session.converter;


import cn.hengzq.orange.ai.common.biz.session.vo.SessionMessageVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionMessageParam;
import cn.hengzq.orange.ai.core.biz.session.entity.SessionMessageEntity;
import cn.hengzq.orange.common.converter.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 聊天会话记录换装器
 *
 * @author hengzq
 */
@Mapper
public interface SessionMessageConverter extends Converter {

    SessionMessageConverter INSTANCE = Mappers.getMapper(SessionMessageConverter.class);

    List<SessionMessageVO> toListVO(List<SessionMessageEntity> entityList);

    SessionMessageEntity toEntity(AddSessionMessageParam param);
}
