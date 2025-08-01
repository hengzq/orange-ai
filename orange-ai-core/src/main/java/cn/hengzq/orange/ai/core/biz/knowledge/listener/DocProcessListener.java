package cn.hengzq.orange.ai.core.biz.knowledge.listener;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.DocRefreshType;
import cn.hengzq.orange.ai.common.biz.knowledge.event.DocRefreshEvent;
import cn.hengzq.orange.ai.common.biz.knowledge.event.DocRefreshParam;
import cn.hengzq.orange.ai.core.biz.knowledge.service.VectorSyncService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 文档处理结监听器
 */
@Slf4j
@Component
@AllArgsConstructor
public class DocProcessListener {

    private final VectorSyncService vectorSyncService;

    /**
     * 切片向量化
     */
    @Async()
    @EventListener(DocRefreshEvent.class)
    public void add(DocRefreshEvent event) {
        try {
            if (event.getSource() instanceof DocRefreshParam param) {
                if (DocRefreshType.ADD.equals(param.getType())) {
                    vectorSyncService.refreshDocByDocIds(param.getDocIds());
                } else {
                    log.warn("params type error.param:{}", event.getSource());
                }
            } else {
                log.warn("params type error.param:{}", event.getSource());
            }
        } catch (Exception e) {
            log.error("doc add is error. msg:{},data:{}", e.getMessage(), event.getSource());
        }
    }

}
