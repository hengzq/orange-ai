package cn.hengzq.orange.ai.core.biz.workflow.entity;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowStatusEnum;
import cn.hengzq.orange.mybatis.entity.BaseTenantEntity;
import cn.hengzq.orange.mybatis.handler.EnumCodeTypeHandler;
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
@TableName(value = "ai_wf", autoResultMap = true)
public class WorkflowEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "wf_status", typeHandler = EnumCodeTypeHandler.class)
    private WorkflowStatusEnum workflowStatus;

    @TableField(value = "draft_version_id")
    private String draftVersionId;

    @TableField(value = "published_version_id")
    private String publishedVersionId;
}
