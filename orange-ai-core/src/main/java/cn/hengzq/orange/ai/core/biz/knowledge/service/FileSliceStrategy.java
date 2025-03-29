package cn.hengzq.orange.ai.core.biz.knowledge.service;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.FileTypeEnum;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.SliceInfo;

import java.util.List;

/**
 * 文件切片解耦
 */
public interface FileSliceStrategy {

    FileTypeEnum getFileType();

    /**
     * 文件切片策略
     */
    List<SliceInfo> split(String fileName);
}
