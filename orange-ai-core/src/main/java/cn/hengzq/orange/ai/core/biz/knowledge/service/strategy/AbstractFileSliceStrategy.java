package cn.hengzq.orange.ai.core.biz.knowledge.service.strategy;

import cn.hengzq.orange.ai.core.biz.knowledge.service.FileSliceStrategy;

/**
 * 文件切片解耦
 */
public abstract class AbstractFileSliceStrategy implements FileSliceStrategy {

    protected static final int MAX_SLICE_SIZE = 1000;

}
