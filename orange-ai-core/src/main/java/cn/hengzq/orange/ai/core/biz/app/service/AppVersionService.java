package cn.hengzq.orange.ai.core.biz.app.service;


import cn.hengzq.orange.ai.common.biz.app.dto.AppVersionVO;
import cn.hengzq.orange.ai.common.biz.app.dto.request.AddAppVersionParam;
import cn.hengzq.orange.ai.common.biz.app.dto.request.AppVersionListParam;
import cn.hengzq.orange.ai.common.biz.app.dto.request.AppVersionPageParam;
import cn.hengzq.orange.ai.common.biz.app.dto.request.UpdateAppVersionParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;
import java.util.Map;

/**
 * @author hengzq
 */
public interface AppVersionService {

    String add(AddAppVersionParam param);

    List<AppVersionVO> list(AppVersionListParam param);

    Map<String, AppVersionVO> mapByIds(List<String> ids);

    AppVersionVO getLatestByAppId(String appId, boolean latest);

    /**
     * 根据应用ID，获取或创建一个快照版本
     *
     * @param appId
     * @return 快照版本
     */
    AppVersionVO getOrCreateSnapshotVersionByAppId(String appId);

    Boolean updatePublishByAppId(String appId);

    Boolean updateById(String id, UpdateAppVersionParam param);

    String addDraftByPublishedId(String id);

    AppVersionVO getById(String id);

    Boolean updatePublishById(String draftVersionId);

    PageDTO<AppVersionVO> page(AppVersionPageParam param);

    List<AppVersionVO> listByAppId(String appId);

    Boolean deleteByAppId(String appId);
}
