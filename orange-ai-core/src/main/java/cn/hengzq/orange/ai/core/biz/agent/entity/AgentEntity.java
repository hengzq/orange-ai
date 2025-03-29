package cn.hengzq.orange.ai.core.biz.agent.entity;

import cn.hengzq.orange.ai.common.biz.model.vo.ModelConfig;
import cn.hengzq.orange.mybatis.entity.BaseTenantEntity;
import cn.hengzq.orange.mybatis.handler.ListToStringTypeHandler;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "ai_agent", autoResultMap = true)
public class AgentEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    @TableField(value = "model_id")
    private String modelId;

    @TableField(value = "model_config", typeHandler = JacksonTypeHandler.class)
    private ModelConfig modelConfig;

    @TableField(value = "base_ids", typeHandler = ListToStringTypeHandler.class)
    private List<String> baseIds;

    @TableField(value = "system_prompt")
    private String systemPrompt;

    private String description;
}
