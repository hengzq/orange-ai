package cn.hengzq.orange.ai.core.biz.chat.entity;

import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.biz.chat.constant.RatingEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hengzq.orange.mybatis.entity.BaseTenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author hengzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "ai_chat_session_record")
public class ChatSessionRecordEntity extends BaseTenantEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    @TableField(value = "user_id")
    private Long userId;


    @TableField(value = "session_id")
    private Long sessionId;

    @TableField(value = "message_type")
    private MessageTypeEnum messageType;

    private String content;

    @TableField(value = "token_quantity")
    private Long tokenQuantity;

    private RatingEnum rating;

}
