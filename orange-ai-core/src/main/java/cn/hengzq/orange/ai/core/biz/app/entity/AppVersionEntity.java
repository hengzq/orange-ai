package cn.hengzq.orange.ai.core.biz.app.entity;

import cn.hengzq.orange.ai.common.biz.app.constant.AppVersionStatusEnum;
import cn.hengzq.orange.ai.common.biz.app.dto.AppBaseConfig;
import cn.hengzq.orange.ai.common.biz.model.dto.ModelConfig;
import cn.hengzq.orange.mybatis.entity.BaseTenantEntity;
import cn.hengzq.orange.mybatis.handler.ListToStringTypeHandler;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "ai_app_version", autoResultMap = true)
public class AppVersionEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "app_id")
    private String appId;

    private String name;

    @TableField(value = "system_prompt")
    private String systemPrompt;

    private String description;

    @TableField(value = "model_id")
    private String modelId;

    @TableField(value = "model_config", typeHandler = JacksonTypeHandler.class)
    private ModelConfig modelConfig;

    @TableField(value = "base_ids", typeHandler = ListToStringTypeHandler.class)
    private List<String> baseIds;

    @TableField(value = "base_config", typeHandler = JacksonTypeHandler.class)
    private AppBaseConfig baseConfig;

    @TableField(value = "mcp_ids", typeHandler = ListToStringTypeHandler.class)
    private List<String> mcpIds;

    @TableField(value = "workflow_ids", typeHandler = ListToStringTypeHandler.class)
    private List<String> workflowIds;

    @TableField(value = "version_status")
    private AppVersionStatusEnum versionStatus;

    @TableField("publish_by")
    private String publishBy;

    @TableField("publish_at")
    private LocalDateTime publishAt;
}
