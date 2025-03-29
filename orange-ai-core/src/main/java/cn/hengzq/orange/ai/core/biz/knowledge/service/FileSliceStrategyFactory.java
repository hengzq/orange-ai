package cn.hengzq.orange.ai.core.biz.knowledge.service;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.FileTypeEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件切片解耦
 */
@Component
public class FileSliceStrategyFactory {
    private final Map<FileTypeEnum, FileSliceStrategy> fileSliceStrategyMap;

    public FileSliceStrategyFactory(List<FileSliceStrategy> strategyList) {
        this.fileSliceStrategyMap = new HashMap<>(strategyList.size());
        strategyList.forEach(strategy -> fileSliceStrategyMap.put(strategy.getFileType(), strategy));
    }

    /**
     * 获取文件切片策略。
     *
     * @param fileType 需要获取切片策略的文件类型。
     * @return 返回对应文件类型的切片策略。
     */
    public FileSliceStrategy getFileSliceStrategy(FileTypeEnum fileType) {
        return fileSliceStrategyMap.get(fileType);
    }
}
