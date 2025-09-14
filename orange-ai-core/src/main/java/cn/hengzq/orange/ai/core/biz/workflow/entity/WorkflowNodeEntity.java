package cn.hengzq.orange.ai.core.biz.workflow.entity;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowNodeTypeEnum;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.NodeInputConfig;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.NodeOutputConfig;
import cn.hengzq.orange.ai.common.biz.workflow.dto.config.Position;
import cn.hengzq.orange.mybatis.entity.BaseTenantEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "ai_wf_node", autoResultMap = true)
public class WorkflowNodeEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "wf_id")
    private String workflowId;

    @TableField(value = "wf_version_id")
    private String workflowVersionId;

    @TableField(value = "node_code")
    private String nodeCode;

    @TableField(value = "name")
    private String name;

    @TableField(value = "node_type")
    private WorkflowNodeTypeEnum nodeType;

    @TableField(value = "description")
    private String description;

    @TableField(value = "position", typeHandler = JacksonTypeHandler.class)
    private Position position;

    @TableField(value = "input_config", typeHandler = JacksonTypeHandler.class)
    private NodeInputConfig inputConfig;

    @TableField(value = "output_config", typeHandler = JacksonTypeHandler.class)
    private NodeOutputConfig outputConfig;
}
