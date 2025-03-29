package cn.hengzq.orange.ai.core.biz.knowledge.mapper;

import cn.hengzq.orange.ai.core.biz.knowledge.entity.KnowledgeDocSliceEntity;
import cn.hengzq.orange.mybatis.mapper.CommonMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hengzq
 */
@Repository
public interface KnowledgeDocSliceMapper extends CommonMapper<KnowledgeDocSliceEntity> {

    @Select("select id from ai_knowledge_doc_slice where doc_id = #{docId}")
    List<String> selectListIdByDocId(String docId);
}
