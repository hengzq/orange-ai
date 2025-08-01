package cn.hengzq.orange.ai.core.biz.mcp.service;

import cn.hengzq.orange.ai.common.biz.mcp.vo.McpServerVO;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.server.transport.HttpServletSseServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class McpClientComponent {

    private final static Cache<String, McpSyncClient> MCP_SYNC_CLIENT_CACHE = CacheUtil.newLFUCache(100);

    public McpSyncClient getMcpSyncClient(McpServerVO mcpServer) {
        if (Objects.isNull(mcpServer) || Objects.isNull(mcpServer.getConnectionUrl())) {
            return null;
        }
        if (MCP_SYNC_CLIENT_CACHE.containsKey(mcpServer.getId())) {
            return MCP_SYNC_CLIENT_CACHE.get(mcpServer.getId());
        }
        try {
            McpSchema.Implementation clientInfo = new McpSchema.Implementation(
                    mcpServer.getName(), "1.0.0");
            HttpClientSseClientTransport transport = HttpClientSseClientTransport
                    .builder(mcpServer.getConnectionUrl())
                    .sseEndpoint(StrUtil.isBlank(mcpServer.getSseEndpoint()) ? HttpServletSseServerTransportProvider.DEFAULT_SSE_ENDPOINT : mcpServer.getSseEndpoint())
                    .build();
            McpSyncClient client = McpClient.sync(transport).clientInfo(clientInfo).build();
            client.initialize();
            MCP_SYNC_CLIENT_CACHE.put(mcpServer.getId(), client);
            return client;
        } catch (Exception e) {
            log.error("McpSyncClient error. mcp server name: {}", mcpServer.getName(), e);
        }
        return null;
    }

    public List<McpSyncClient> getMcpSyncClients(List<McpServerVO> mcpServerList) {
        if (CollUtil.isEmpty(mcpServerList)) {
            return List.of();
        }
        List<McpSyncClient> clients = new ArrayList<>();
        for (McpServerVO vo : mcpServerList) {
            if (Objects.isNull(vo) || Objects.isNull(vo.getConnectionUrl())) {
                continue;
            }
            if (MCP_SYNC_CLIENT_CACHE.containsKey(vo.getId())) {
                clients.add(MCP_SYNC_CLIENT_CACHE.get(vo.getId()));
                continue;
            }
            try {
                McpSchema.Implementation clientInfo = new McpSchema.Implementation(
                        vo.getName(), "1.0.0");
                HttpClientSseClientTransport transport = HttpClientSseClientTransport.builder(vo.getConnectionUrl())
                        .sseEndpoint(StrUtil.isBlank(vo.getSseEndpoint()) ? HttpServletSseServerTransportProvider.DEFAULT_SSE_ENDPOINT : vo.getSseEndpoint()).build();
                McpSyncClient client = McpClient.sync(transport).clientInfo(clientInfo).build();
                client.initialize();
                MCP_SYNC_CLIENT_CACHE.put(vo.getId(), client);
                clients.add(client);
            } catch (Exception e) {
                log.error("McpSyncClient error. mcp server name: {}", vo.getName(), e);
            }
        }
        return clients;
    }

}
