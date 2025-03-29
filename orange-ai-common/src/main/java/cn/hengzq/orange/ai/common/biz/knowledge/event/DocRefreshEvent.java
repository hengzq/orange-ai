package cn.hengzq.orange.ai.common.biz.knowledge.event;

import org.springframework.context.ApplicationEvent;

/**
 * 文档刷新事件
 */
public class DocRefreshEvent extends ApplicationEvent {

    public DocRefreshEvent(DocRefreshParam param) {
        super(param);
    }
}
