package cn.hengzq.orange.ai.core.biz.mcp.service;


import cn.hengzq.orange.ai.common.biz.mcp.vo.McpServerVO;
import cn.hengzq.orange.ai.common.biz.mcp.vo.McpToolVO;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.AddMcpServerParam;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.McpServerListParam;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.McpServerPageParam;
import cn.hengzq.orange.ai.common.biz.mcp.vo.param.UpdateMcpServerParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;

/**
 * @author hengzq
 */
public interface McpServerService {

    String add(AddMcpServerParam request);

    Boolean removeById(String id);

    Boolean updateById(String id, UpdateMcpServerParam request);

    Boolean updateEnabledById(String id, boolean enabled);

    McpServerVO getById(String id);

    List<McpServerVO> list(McpServerListParam query);

    PageDTO<McpServerVO> page(McpServerPageParam param);

    List<McpToolVO> listToolById(String id);
}
