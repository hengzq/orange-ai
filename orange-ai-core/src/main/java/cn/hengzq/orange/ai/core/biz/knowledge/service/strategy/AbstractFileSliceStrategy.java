package cn.hengzq.orange.ai.core.biz.knowledge.service.strategy;

import cn.hengzq.orange.ai.core.biz.knowledge.service.FileSliceStrategy;
import cn.hengzq.orange.storage.StorageService;
import cn.hengzq.orange.storage.StorageServiceFactory;
import cn.hutool.core.util.StrUtil;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * 文件切片解耦
 */
public abstract class AbstractFileSliceStrategy implements FileSliceStrategy {

    protected static final int MAX_SLICE_SIZE = 1000;

    public abstract DocumentReader getDocumentReader(File file);

    protected File getByFileName(String fileName) {
        StorageService storageService = StorageServiceFactory.getDefaultStorageService();
        return storageService.getFileByFileName(fileName);
    }

    @Override
    public List<Document> read(String fileName) {
        DocumentReader reader = getDocumentReader(fileName);
        if (Objects.isNull(reader)) {
            return null;
        }
        return reader.read();
    }

    @Override
    public DocumentReader getDocumentReader(String fileName) {
        if (StrUtil.isBlank(fileName)) {
            return null;
        }
        File file = getByFileName(fileName);
        if (Objects.isNull(file) || !file.exists()) {
            return null;
        }
        return getDocumentReader(file);
    }
}
