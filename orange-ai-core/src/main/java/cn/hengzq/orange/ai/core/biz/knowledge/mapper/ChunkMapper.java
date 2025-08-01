package cn.hengzq.orange.ai.core.biz.knowledge.mapper;

import cn.hengzq.orange.ai.core.biz.knowledge.entity.ChunkEntity;
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
public interface ChunkMapper extends CommonMapper<ChunkEntity> {

    default List<ChunkEntity> selectListByDocId(String docId) {
        if (StrUtil.isBlank(docId)) {
            return Collections.emptyList();
        }
        return this.selectList(CommonWrappers.<ChunkEntity>lambdaQuery().eq(ChunkEntity::getDocId, docId));
    }

    default List<ChunkEntity> selectListByBaseId(String baseId) {
        if (StrUtil.isBlank(baseId)) {
            return Collections.emptyList();
        }
        return this.selectList(CommonWrappers.<ChunkEntity>lambdaQuery().eq(ChunkEntity::getBaseId, baseId));
    }
}
