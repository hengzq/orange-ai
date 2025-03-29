package cn.hengzq.orange.ai.core.biz.knowledge.entity;

import cn.hengzq.orange.mybatis.entity.BaseTenantEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "ai_knowledge_base")
public class KnowledgeBaseEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    @TableField(value = "embedding_model_id")
    private String embeddingModelId;

    private boolean enabled;

    private Integer sort;

    private String description;

    @TableField(value = "vector_collection_name")
    private String vectorCollectionName;
}
