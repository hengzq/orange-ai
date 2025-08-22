package cn.hengzq.orange.ai.core.biz.workflow.entity;

import cn.hengzq.orange.ai.common.biz.workflow.constant.WorkflowRunScopeEnum;
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
@TableName(value = "ai_wf_run", autoResultMap = true)
public class WorkflowRunEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "wf_id")
    private String workflowId;

    @TableField(value = "wf_version_id")
    private String workflowVersionId;

    @TableField(value = "run_scope", typeHandler = EnumCodeTypeHandler.class)
    private WorkflowRunScopeEnum runScope;

    @TableField(value = "run_status", typeHandler = EnumCodeTypeHandler.class)
    private WorkflowRunStatusEnum runStatus;

    @TableField(value = "input_data", typeHandler = FastjsonTypeHandler.class)
    private JSONObject inputData;

    @TableField(value = "output_data", typeHandler = FastjsonTypeHandler.class)
    private JSONObject outputData;


}
