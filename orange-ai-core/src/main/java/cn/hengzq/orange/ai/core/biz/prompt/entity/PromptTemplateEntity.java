package cn.hengzq.orange.ai.core.biz.prompt.entity;

import cn.hengzq.orange.ai.common.biz.prompt.constant.PromptTemplateEnum;
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
@TableName(value = "ai_prompt_template")
public class PromptTemplateEntity extends BaseTenantEntity {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;


    private String name;

    @TableField(value = "template_type", typeHandler = EnumCodeTypeHandler.class)
    private PromptTemplateEnum templateType;

    @TableField(value = "template_content")
    private String templateContent;


}
