package cn.hengzq.orange.ai.core.biz.mcp.service.impl;

import cn.hengzq.orange.ai.common.biz.mcp.vo.McpServerVO;
import cn.hengzq.orange.ai.common.biz.mcp.vo.McpToolVO;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.AddMcpServerParam;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.McpServerListParam;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.McpServerPageParam;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.UpdateMcpServerParam;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.core.biz.mcp.converter.McpServerConverter;
import cn.hengzq.orange.ai.core.biz.mcp.entity.McpServerEntity;
import cn.hengzq.orange.ai.core.biz.mcp.mapper.McpServerMapper;
import cn.hengzq.orange.ai.core.biz.mcp.service.McpClientComponent;
import cn.hengzq.orange.ai.core.biz.mcp.service.McpServerService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.util.Assert;
import cn.hengzq.orange.mybatis.entity.BaseEntity;
import cn.hengzq.orange.mybatis.query.CommonWrappers;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class McpServerServiceImpl implements McpServerService {

    private final McpServerMapper mcpServerMapper;

    private final McpClientComponent mcpClientComponent;

    @Override
    public Boolean removeById(String id) {
        McpServerEntity entity = mcpServerMapper.selectById(id);
        if (Objects.isNull(entity)) {
            return Boolean.TRUE;
        }
        mcpServerMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public String add(AddMcpServerParam request) {
        McpServerEntity entity = McpServerConverter.INSTANCE.toEntity(request);
        return mcpServerMapper.insertOne(entity);
    }

    @Override
    public Boolean updateById(String id, UpdateMcpServerParam request) {
        McpServerEntity entity = mcpServerMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity = McpServerConverter.INSTANCE.toUpdateEntity(entity, request);
        return mcpServerMapper.updateOneById(entity);
    }

    @Override
    public Boolean updateEnabledById(String id, boolean enabled) {
        McpServerEntity entity = mcpServerMapper.selectById(id);
        Assert.nonNull(entity, AIModelErrorCode.GLOBAL_DATA_NOT_EXIST);
        entity.setEnabled(enabled);
        return mcpServerMapper.updateOneById(entity);
    }

    @Override
    public McpServerVO getById(String id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return McpServerConverter.INSTANCE.toVO(mcpServerMapper.selectById(id));
    }

    @Override
    public List<McpServerVO> list(McpServerListParam param) {
        List<McpServerEntity> entityList = mcpServerMapper.selectList(
                CommonWrappers.<McpServerEntity>lambdaQuery()
                        .eqIfPresent(McpServerEntity::getEnabled, param.getEnabled())
                        .inIfPresent(McpServerEntity::getId, param.getIds())
                        .likeIfPresent(McpServerEntity::getName, param.getName())
                        .orderByDesc(BaseEntity::getCreatedAt)
        );
        return McpServerConverter.INSTANCE.toListVO(entityList);
    }

    @Override
    public PageDTO<McpServerVO> page(McpServerPageParam param) {
        PageDTO<McpServerEntity> page = mcpServerMapper.selectPage(param, CommonWrappers.<McpServerEntity>lambdaQuery()
                .eqIfPresent(McpServerEntity::getEnabled, param.getEnabled())
                .likeIfPresent(McpServerEntity::getName, param.getName())
                .orderByDesc(McpServerEntity::getCreatedAt));
        return McpServerConverter.INSTANCE.toPage(page);
    }

    @Override
    public List<McpToolVO> listToolById(String id) {
        McpServerVO mcpServer = getById(id);
        if (Objects.isNull(mcpServer)) {
            return List.of();
        }
        McpSyncClient client = mcpClientComponent.getMcpSyncClient(mcpServer);
        if (Objects.isNull(client)) {
            return List.of();
        }
        McpSchema.ListToolsResult result = client.listTools();
        return McpServerConverter.INSTANCE.toListTool(result.tools());

    }
}
