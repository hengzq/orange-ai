package cn.hengzq.orange.ai.core.biz.app.service;


import cn.hengzq.orange.ai.common.biz.app.dto.AppListResponse;
import cn.hengzq.orange.ai.common.biz.app.dto.AppVO;
import cn.hengzq.orange.ai.common.biz.app.dto.request.*;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.response.ApiResponse;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author hengzq
 */
public interface AppService {

    String add(AppCreateRequest param);

    Boolean removeById(String id);

    Boolean updateById(String id, AppUpdateRequest request);

    Boolean updatePublishById(String id);

    AppVO getById(String id);

    List<AppVO> list(AppListParam query);

    PageDTO<AppListResponse> pageApps(AppPageRequest param);

    Flux<ApiResponse<ConversationResponse>> conversationStream(AppConversationStreamParam param);

    AppVO getLatestById(String id, boolean latestReleased);

}
