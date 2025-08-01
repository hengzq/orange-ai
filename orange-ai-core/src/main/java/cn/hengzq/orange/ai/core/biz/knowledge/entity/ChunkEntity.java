package cn.hengzq.orange.ai.core.biz.knowledge.entity;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.SliceEmbStatus;
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
@TableName(value = "ai_kb_doc_chunk")
public class ChunkEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("base_id")
    private String baseId;

    @TableField("doc_id")
    private String docId;

    @TableField("emb_status")
    private SliceEmbStatus embStatus;

    @TableField("text")
    private String text;

}
