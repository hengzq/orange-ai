package cn.hengzq.orange.ai.core.biz.model.entity;

import cn.hengzq.orange.ai.common.constant.ModelTypeEnum;
import cn.hengzq.orange.mybatis.handler.EnumCodeTypeHandler;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.mybatis.entity.BaseTenantEntity;
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
    private Long id;


    @TableField(value = "platform", typeHandler = EnumCodeTypeHandler.class)
    private PlatformEnum platform;

    private String name;

    private String code;

    @TableField(value = "type", typeHandler = EnumCodeTypeHandler.class)
    private ModelTypeEnum type;

    private boolean enabled;

    private Integer sort;

    private String description;
}
