package cn.hengzq.orange.ai.core.biz.mcp.entity;

import cn.hengzq.orange.ai.common.biz.mcp.constant.TransportProtocolEnum;
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
@TableName(value = "ai_mcp_server")
public class McpServerEntity extends BaseTenantEntity {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    @TableField(value = "transport_protocol", typeHandler = EnumCodeTypeHandler.class)
    private TransportProtocolEnum transportProtocol;

    @TableField(value = "connection_url")
    private String connectionUrl;

    @TableField(value = "sse_endpoint")
    private String sseEndpoint;

    private Boolean enabled;

    private String description;
}
