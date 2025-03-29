package cn.hengzq.orange.ai.core.biz.knowledge.service.strategy;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.FileTypeEnum;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.SliceInfo;
import cn.hengzq.orange.storage.StorageService;
import cn.hengzq.orange.storage.StorageServiceFactory;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TxtFileSliceStrategy extends AbstractFileSliceStrategy {
    @Override
    public FileTypeEnum getFileType() {
        return FileTypeEnum.TXT;
    }

    @Override
    public List<SliceInfo> split(String fileName) {
        StorageService storageService = StorageServiceFactory.getDefaultStorageService();
        File file = storageService.getFileByFileName(fileName);

        FileReader fileReader = new FileReader(file);
        List<String> sensitiveWords = fileReader.readLines();
        return sensitiveWords.stream()
                .filter(StrUtil::isNotBlank)
                .map(sensitiveWord -> SliceInfo.builder().content(sensitiveWord).build()).collect(Collectors.toList());
    }
}
