package cn.hengzq.orange.ai.core.biz.knowledge.mapper;

import cn.hengzq.orange.ai.core.biz.knowledge.entity.DocEntity;
import cn.hengzq.orange.mybatis.mapper.CommonMapper;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author hengzq
 */
@Repository
public interface DocMapper extends CommonMapper<DocEntity> {


    default List<DocEntity> selectByBaseId(String baseId) {
        if (StrUtil.isBlank(baseId)) {
            return Collections.emptyList();
        }
        return selectList(CommonWrappers.<DocEntity>lambdaQuery().eq(DocEntity::getBaseId, baseId));
    }
}
