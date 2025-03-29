package cn.hengzq.orange.ai.core.biz.session.entity;

import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import cn.hengzq.orange.mybatis.entity.BaseTenantEntity;
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
@TableName(value = "ai_session")
public class SessionEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "user_id")
    private String userId;

    @TableField(value = "association_id")
    private String associationId;

    /**
     * 会话名称
     */
    private String name;

    @TableField(value = "model_id")
    private String modelId;

    @TableField(value = "session_type")
    private SessionTypeEnum sessionType;


}
