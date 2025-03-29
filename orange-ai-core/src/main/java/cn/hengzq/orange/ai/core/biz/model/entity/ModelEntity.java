package cn.hengzq.orange.ai.core.biz.model.entity;

import cn.hengzq.orange.ai.common.constant.ModelTypeEnum;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
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
@TableName(value = "ai_model")
public class ModelEntity extends BaseTenantEntity {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;


    @TableField(value = "platform", typeHandler = EnumCodeTypeHandler.class)
    private PlatformEnum platform;

    private String name;

    @TableField("model_name")
    private String modelName;

    @TableField(value = "model_type", typeHandler = EnumCodeTypeHandler.class)
    private ModelTypeEnum modelType;

    private boolean enabled;

    private Integer sort;


    @TableField(value = "api_key")
    private String apiKey;

    private String description;
}
