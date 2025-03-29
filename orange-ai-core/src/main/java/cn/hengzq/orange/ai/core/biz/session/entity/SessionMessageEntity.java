package cn.hengzq.orange.ai.core.biz.session.entity;

import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
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
@TableName(value = "ai_session_message")
public class SessionMessageEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "parent_id")
    private Long parentId;

    @TableField(value = "session_id")
    private Long sessionId;

    @TableField(value = "role")
    private MessageTypeEnum role;

    private String content;

}
