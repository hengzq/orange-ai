package cn.hengzq.orange.ai.core.biz.app.entity;

import cn.hengzq.orange.ai.common.biz.app.constant.AppStatusEnum;
import cn.hengzq.orange.ai.common.biz.app.constant.AppTypeEnum;
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
@TableName(value = "ai_app", autoResultMap = true)
public class AppEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "app_type", typeHandler = EnumCodeTypeHandler.class)
    private AppTypeEnum appType;

    @TableField(value = "app_status", typeHandler = EnumCodeTypeHandler.class)
    private AppStatusEnum appStatus;

    @TableField(value = "draft_version_id")
    private String draftVersionId;

    @TableField(value = "published_version_id")
    private String publishedVersionId;
}
