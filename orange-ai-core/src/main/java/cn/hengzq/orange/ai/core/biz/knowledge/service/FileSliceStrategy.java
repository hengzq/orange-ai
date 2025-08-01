package cn.hengzq.orange.ai.core.biz.knowledge.service;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.FileTypeEnum;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.ChunkVO;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;

import java.io.File;
import java.util.List;

/**
 * 文件切片解耦
 */
public interface FileSliceStrategy {

    FileTypeEnum getFileType();

    /**
     * 文件切片策略
     */
    List<ChunkVO> split(String fileName);

    List<Document> read(String fileName);

    DocumentReader getDocumentReader(String fileName);

    DocumentReader getDocumentReader(File file);
}
