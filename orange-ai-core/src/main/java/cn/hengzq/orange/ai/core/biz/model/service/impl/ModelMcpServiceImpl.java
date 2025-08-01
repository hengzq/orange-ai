package cn.hengzq.orange.ai.core.biz.model.service.impl;

import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.model.vo.param.ModelListParam;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.common.service.mcp.McpServerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author hengzq
 */
@Slf4j
@Service
@AllArgsConstructor
public class ModelMcpServiceImpl implements McpServerService {

    private final ModelService modelService;

    /**
     * 获取当前系统中所有可用的模型列表。返回包含模型ID、所属平台、模型类型、名称、状态等详细信息的模型元数据列表，用于查询和选择可调用的AI模型。
     */
    @Tool(name = "model_list",
            description = "Retrieves a list of all available models in the current system. " +
                    "Returns metadata for each model, including model ID, platform, model type, name, and status. " +
                    "Used for querying and selecting AI models that can be invoked.")
    public List<ModelVO> list() {
        return modelService.list(ModelListParam.builder().build());
    }

}
