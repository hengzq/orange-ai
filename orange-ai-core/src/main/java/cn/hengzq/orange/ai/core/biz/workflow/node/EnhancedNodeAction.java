package cn.hengzq.orange.ai.core.biz.workflow.node;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
@Builder
@RequiredArgsConstructor
public class EnhancedNodeAction implements NodeAction {

    /**
     * 节点ID
     */
    private final String nodeId;

    /**
     * 核心逻辑
     */
    private final NodeAction delegate;

    /**
     * 前置操作
     */
    private final Consumer<OverAllState> before;

    /**
     * 后置操作
     */
    private final BiConsumer<OverAllState, Map<String, Object>> after;

    /**
     * 异常处理
     */
    private final BiConsumer<OverAllState, Exception> onError;


    @Override
    public Map<String, Object> apply(OverAllState state) {
        try {
            // 执行前置
            if (before != null) {
                before.accept(state);
            }
            // 执行核心逻辑
            Map<String, Object> result = delegate.apply(state);

            // 执行后置
            if (after != null) {
                after.accept(state, result);
            }

            return result;
        } catch (Exception e) {
            log.warn("enhanced node action error", e);
            if (onError != null) {
                onError.accept(state, e);
            }

        }
        return null;
    }
}
