package cn.hengzq.orange.ai.core.biz.workflow.entity;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowRunStatusEnum;
import cn.hengzq.orange.mybatis.entity.BaseTenantEntity;
import cn.hengzq.orange.mybatis.handler.EnumCodeTypeHandler;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "ai_wf_node_run", autoResultMap = true)
public class WorkflowNodeRunEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "run_id")
    private String runId;

    @TableField(value = "node_id")
    private String nodeId;

    @TableField(value = "run_status", typeHandler = EnumCodeTypeHandler.class)
    private WorkflowRunStatusEnum runStatus;

    @TableField(value = "run_context", typeHandler = FastjsonTypeHandler.class)
    private JSONObject runContext;

    @TableField(value = "input_data", typeHandler = FastjsonTypeHandler.class)
    private JSONObject inputData;

    @TableField(value = "output_data", typeHandler = FastjsonTypeHandler.class)
    private JSONObject outputData;

    @TableField("error_msg")
    private String errorMsg;
}
