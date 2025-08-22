package cn.hengzq.orange.ai.core.biz.workflow.entity;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowVersionStatusEnum;
import cn.hengzq.orange.mybatis.entity.BaseTenantEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "ai_wf_version", autoResultMap = true)
public class WorkflowVersionEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "wf_id")
    private String workflowId;

    private String name;

    private String description;

    @TableField(value = "version_status")
    private WorkflowVersionStatusEnum versionStatus;

    @TableField("publish_by")
    private String publishBy;

    @TableField("publish_at")
    private LocalDateTime publishAt;
}
