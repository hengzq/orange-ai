package cn.hengzq.orange.ai.core.biz.workflow.entity;

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
@TableName(value = "ai_wf_edge", autoResultMap = true)
public class WorkflowEdgeEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "wf_id")
    private String workflowId;

    @TableField(value = "wf_version_id")
    private String workflowVersionId;

    @TableField(value = "source_node_id")
    private String sourceNodeId;

    @TableField(value = "target_node_id")
    private String targetNodeId;
}
