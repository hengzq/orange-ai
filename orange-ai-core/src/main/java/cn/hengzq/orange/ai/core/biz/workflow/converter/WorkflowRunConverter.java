package cn.hengzq.orange.ai.core.biz.workflow.converter;


import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowRunDetailVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.WorkflowRunVO;
import cn.hengzq.orange.ai.common.biz.workflow.dto.request.CreateWorkflowRunParam;
import cn.hengzq.orange.ai.common.biz.workflow.dto.result.WorkflowRunResult;
import cn.hengzq.orange.ai.core.biz.workflow.entity.WorkflowRunEntity;
import cn.hengzq.orange.common.converter.Converter;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.ai.graph.KeyStrategy;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.state.strategy.AppendStrategy;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;
import com.alibaba.fastjson2.JSONObject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hengzq
 */
@Mapper
public interface WorkflowRunConverter extends Converter {

    WorkflowRunConverter INSTANCE = Mappers.getMapper(WorkflowRunConverter.class);


    default WorkflowRunResult toResult(WorkflowRunEntity entity) {
        if (entity == null) {
            return null;
        }
        WorkflowRunResult result = new WorkflowRunResult();
        result.setWorkflowId(entity.getWorkflowId());
        result.setId(entity.getId());
        return result;
    }

    WorkflowRunEntity toEntity(CreateWorkflowRunParam param);

    WorkflowRunVO toVO(WorkflowRunEntity entity);

    WorkflowRunDetailVO toDetailVO(WorkflowRunEntity entity);

    default JSONObject toJson(OverAllState state) {
        if (state == null) {
            return null;
        }
        JSONObject result = new JSONObject();
        result.put("data", state.data());

        Map<String, KeyStrategy> keyStrategyMap = state.keyStrategies();
        if (CollUtil.isNotEmpty(keyStrategyMap)) {
            Map<String, String> keyStrategies = new HashMap<>(keyStrategyMap.size());

            keyStrategyMap.forEach((key, strategy) -> {
                if (strategy instanceof AppendStrategy) {
                    keyStrategies.put(key, "AppendStrategy");
                } else if (strategy instanceof ReplaceStrategy) {
                    keyStrategies.put(key, "ReplaceStrategy");
                }
            });
            result.put("keyStrategies", keyStrategies);
        }
        return result;
    }

}
