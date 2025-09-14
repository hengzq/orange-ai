package cn.hengzq.orange.ai.core.biz.app.service;


import cn.hengzq.orange.ai.common.biz.app.vo.AppVO;
import cn.hengzq.orange.ai.common.biz.app.vo.param.*;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.result.Result;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author hengzq
 */
public interface AppService {

    String add(AddAppParam param);

    Boolean removeById(String id);

    Boolean updateById(String id, UpdateAppParam request);

    Boolean updatePublishById(String id);

    AppVO getById(String id);

    List<AppVO> list(AppListParam query);

    PageDTO<AppVO> page(WorkflowPageRequest param);

    Flux<Result<ConversationResponse>> conversationStream(AppConversationStreamParam param);

    AppVO getLatestById(String id, boolean latestReleased);

}
