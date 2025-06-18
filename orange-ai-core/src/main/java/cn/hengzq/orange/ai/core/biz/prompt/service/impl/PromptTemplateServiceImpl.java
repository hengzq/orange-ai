package cn.hengzq.orange.ai.core.biz.prompt.service.impl;

import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.prompt.vo.PromptTemplateVO;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.AddPromptTemplateParam;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.PromptTemplateListParam;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.PromptTemplatePageParam;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.UpdatePromptTemplateParam;
import cn.hengzq.orange.ai.core.biz.prompt.converter.PromptTemplateConverter;
import cn.hengzq.orange.ai.core.biz.prompt.entity.PromptTemplateEntity;
import cn.hengzq.orange.ai.core.biz.prompt.mapper.PromptTemplateMapper;
import cn.hengzq.orange.ai.core.biz.prompt.service.PromptTemplateService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.common.util.CollUtils;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class PromptTemplateServiceImpl implements PromptTemplateService {


    private final PromptTemplateMapper promptTemplateMapper;

    @Override
    public Boolean removeById(String id) {
        PromptTemplateEntity entity = promptTemplateMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return Boolean.TRUE;
        }
        promptTemplateMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public String add(AddPromptTemplateParam param) {
        PromptTemplateEntity entity = PromptTemplateConverter.INSTANCE.toEntity(param);
        return promptTemplateMapper.insertOne(entity);
    }

    @Override
    public Boolean updateById(String id, UpdatePromptTemplateParam param) {
        PromptTemplateEntity entity = promptTemplateMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity = PromptTemplateConverter.INSTANCE.toUpdateEntity(entity, param);
        return promptTemplateMapper.updateOneById(entity);
    }

    @Override
    public PromptTemplateVO getById(String id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return PromptTemplateConverter.INSTANCE.toVO(promptTemplateMapper.selectById(id));
    }

    @Override
    public List<PromptTemplateVO> list(PromptTemplateListParam param) {
        List<PromptTemplateEntity> entityList = promptTemplateMapper.selectList(
                CommonWrappers.<PromptTemplateEntity>lambdaQuery()
                        .likeIfPresent(PromptTemplateEntity::getName, param.getName())
        );
        return PromptTemplateConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public PageDTO<PromptTemplateVO> page(PromptTemplatePageParam param) {
        PageDTO<PromptTemplateEntity> page = promptTemplateMapper.selectPage(param, CommonWrappers.<PromptTemplateEntity>lambdaQuery()
                .likeIfPresent(PromptTemplateEntity::getName, param.getName())
        );
        return PromptTemplateConverter.INSTANCE.toPage(page);
    }

    @Override
    public Map<String, PromptTemplateVO> mapModelByIds(List<String> modelIds) {
        if (CollUtil.isEmpty(modelIds)) {
            return Map.of();
        }
        List<PromptTemplateEntity> entityList = promptTemplateMapper.selectList(CommonWrappers.<PromptTemplateEntity>lambdaQuery().in(PromptTemplateEntity::getId, modelIds));
        List<PromptTemplateVO> modelList = PromptTemplateConverter.INSTANCE.toListVO(entityList);
        return CollUtils.convertMap(modelList, PromptTemplateVO::getId, PromptTemplateVO -> PromptTemplateVO);
    }


}
