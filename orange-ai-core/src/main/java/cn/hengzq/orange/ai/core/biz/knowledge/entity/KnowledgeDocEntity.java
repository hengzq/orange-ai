package cn.hengzq.orange.ai.core.biz.knowledge.entity;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.FileStatusEnum;
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
@TableName(value = "ai_knowledge_doc")
public class KnowledgeDocEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("base_id")
    private String baseId;

    @TableField("file_name")
    private String fileName;

    @TableField("file_path")
    private String filePath;

    @TableField("file_size")
    private Long fileSize;

    @TableField("file_type")
    private String fileType;

    @TableField("file_status")
    private FileStatusEnum fileStatus;
}
