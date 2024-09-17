package cn.hengzq.orange.ai.core.core.chat.entity;

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
@TableName(value = "ai_chat_session")
public class ChatSessionEntity extends BaseTenantEntity {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    /**
     * 会话名称
     */
    private String name;

    @TableField(value = "platform")
    private PlatformEnum platform;

    /**
     * 模型编码
     */
    @TableField(value = "model_code")
    private String modelCode;

}
