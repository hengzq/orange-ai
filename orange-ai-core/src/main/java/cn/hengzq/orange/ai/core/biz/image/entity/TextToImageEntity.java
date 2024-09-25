package cn.hengzq.orange.ai.core.biz.image.entity;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.mybatis.entity.BaseTenantEntity;
import cn.hengzq.orange.mybatis.handler.ListToStringTypeHandler;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "ai_text_to_image")
public class TextToImageEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "platform")
    private PlatformEnum platform;

    @TableField(value = "model_code")
    private String modelCode;

    private String prompt;

    private Integer quantity;

    private Integer width;

    private Integer height;

    @TableField(value = "urls", typeHandler = ListToStringTypeHandler.class)
    private List<String> urls;

}
