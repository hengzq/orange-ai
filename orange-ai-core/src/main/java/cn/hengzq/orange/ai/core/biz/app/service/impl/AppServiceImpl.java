package cn.hengzq.orange.ai.core.biz.app.service.impl;

import cn.hengzq.orange.ai.common.biz.app.constant.AppStatusEnum;
import cn.hengzq.orange.ai.common.biz.app.dto.AppListResponse;
import cn.hengzq.orange.ai.common.biz.app.dto.AppVO;
import cn.hengzq.orange.ai.common.biz.app.dto.AppVersionVO;
import cn.hengzq.orange.ai.common.biz.app.dto.request.*;
import cn.hengzq.orange.ai.common.biz.chat.vo.ConversationResponse;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ChatConversationParam;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import cn.hengzq.orange.ai.core.biz.app.converter.AppConverter;
import cn.hengzq.orange.ai.core.biz.app.converter.AppVersionConverter;
import cn.hengzq.orange.ai.core.biz.app.entity.AppEntity;
import cn.hengzq.orange.ai.core.biz.app.mapper.AppMapper;
import cn.hengzq.orange.ai.core.biz.app.service.AppService;
import cn.hengzq.orange.ai.core.biz.app.service.AppVersionService;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatService;
import cn.hengzq.orange.common.constant.PageConstant;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.response.ApiResponse;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.mybatis.entity.BaseEntity;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class AppServiceImpl implements AppService {

    private final AppMapper appMapper;

    private final AppVersionService appVersionService;

    private final ChatService chatService;

    @Override
    public Boolean removeById(String id) {
        AppEntity entity = appMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return Boolean.TRUE;
        }
        appMapper.deleteById(id);
        // 删除关联版本数据
        appVersionService.deleteByAppId(id);
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public String add(AppCreateRequest param) {
        String appId = IdUtil.getSnowflakeNextIdStr();
        String draftVersionId = appVersionService.add(AddAppVersionParam.builder()
                .appId(appId)
                .name(param.getName())
                .description(param.getDescription())
                .build());
        AppEntity entity = new AppEntity();
        entity.setId(appId);
        entity.setDraftVersionId(draftVersionId);
        entity.setAppType(param.getAppType());
        entity.setAppStatus(AppStatusEnum.DRAFT);
        return appMapper.insertOne(entity);
    }

    @Override
    public Boolean updateById(String id, AppUpdateRequest param) {
        AppEntity entity = appMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);

        // 存在草稿版，直接更新。
        String draftVersionId = entity.getDraftVersionId();
        if (StrUtil.isBlank(draftVersionId)) {
            // 不存在创建草稿版
            draftVersionId = appVersionService.addDraftByPublishedId(entity.getPublishedVersionId());
        }
        appVersionService.updateById(draftVersionId, AppVersionConverter.INSTANCE.toUpdate(param));

        // 更新应用状态
        entity.setDraftVersionId(draftVersionId);
        AppStatusEnum appStatus = Objects.isNull(entity.getAppStatus()) ? AppStatusEnum.DRAFT :
                (AppStatusEnum.PUBLISHED.equals(entity.getAppStatus()) ? AppStatusEnum.PUBLISHED_EDITING : entity.getAppStatus());
        entity.setAppStatus(appStatus);

        return appMapper.updateOneById(entity);
    }

    @Override
    public Boolean updatePublishById(String id) {
        AppEntity entity = appMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        String draftVersionId = entity.getDraftVersionId();
        appVersionService.updatePublishById(draftVersionId);

        entity.setDraftVersionId("");
        entity.setPublishedVersionId(draftVersionId);
        entity.setAppStatus(AppStatusEnum.PUBLISHED);
        return appMapper.updateOneById(entity);
    }

    @Override
    public AppVO getById(String id) {
        return AppConverter.INSTANCE.toVO(appMapper.selectById(id));
    }

    @Override
    public Flux<ApiResponse<ConversationResponse>> conversationStream(AppConversationStreamParam param) {
        AppVO app = getLatestById(param.getAppId(), SessionTypeEnum.AGENT.equals(param.getSessionType()));
        AppVersionVO latestVersion = app.getLatestVersion();

        // 构建对话参数
        ChatConversationParam.ChatConversationParamBuilder paramBuilder = ChatConversationParam.builder()
                .prompt(param.getPrompt())
                .sessionId(param.getSessionId())
                .sessionAssociationId(param.getAppId())
                .sessionType(param.getSessionType())
                .systemPrompt(latestVersion.getSystemPrompt())
                .modelId(latestVersion.getModelId())
                .modelConfig(latestVersion.getModelConfig())
                .baseIds(latestVersion.getBaseIds())
                .mcpIds(latestVersion.getMcpIds());
        return chatService.conversationStream(paramBuilder.build());
    }

    @Override
    public AppVO getLatestById(String id, boolean latestReleased) {
        AppVO app = AppConverter.INSTANCE.toVO(appMapper.selectById(id));
        if (Objects.isNull(app)) {
            log.warn("data is not exist id:{}", id);
            return null;
        }
        String versionId = latestReleased ? app.getPublishedVersionId() : (StrUtil.isBlank(app.getDraftVersionId()) ? app.getPublishedVersionId() : app.getDraftVersionId());
        app.setLatestVersion(appVersionService.getById(versionId));
        return app;
    }


    @Override
    public List<AppVO> list(AppListParam param) {
        List<AppEntity> entityList = appMapper.selectList(
                CommonWrappers.<AppEntity>lambdaQuery()
//                        .likeIfPresent(AppEntity::getName, param.getName())
                        .orderByDesc(BaseEntity::getCreatedAt)
        );
        return assembleList(AppConverter.INSTANCE.toListVO(entityList), false);
    }

    @Override
    public PageDTO<AppListResponse> pageApps(AppPageRequest request) {
        Integer pageNo = Objects.isNull(request.getPageNo()) ? PageConstant.PAGE_NO : request.getPageNo();
        Integer pageSize = Objects.isNull(request.getPageSize()) ? PageConstant.PAGE_SIZE : request.getPageSize();

        IPage<AppListResponse> page = appMapper.selectAppPage(new Page<>((long) pageNo, (long) pageSize), request);

        return PageDTO.of(pageNo, pageSize, (int) page.getTotal(), page.getRecords());
    }

    private List<AppVO> assembleList(List<AppVO> records, boolean latestReleased) {
        if (CollUtil.isEmpty(records)) {
            return records;
        }
        List<String> appIds = records.stream().map(item ->
                        latestReleased ? item.getPublishedVersionId() : (StrUtil.isBlank(item.getDraftVersionId()) ? item.getPublishedVersionId() : item.getDraftVersionId()))
                .toList();

        Map<String, AppVersionVO> versionMap = appVersionService.mapByIds(appIds);

        for (AppVO vo : records) {
            if (versionMap.containsKey(vo.getDraftVersionId())) {
                vo.setLatestVersion(versionMap.get(vo.getDraftVersionId()));
            } else if (versionMap.containsKey(vo.getPublishedVersionId())) {
                vo.setLatestVersion(versionMap.get(vo.getPublishedVersionId()));
            }

        }
        return records;
    }

}
