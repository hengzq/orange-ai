package cn.hengzq.orange.ai.core.biz.knowledge.service.strategy;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.FileTypeEnum;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.SliceInfo;
import cn.hengzq.orange.storage.StorageService;
import cn.hengzq.orange.storage.StorageServiceFactory;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DocxFileSliceStrategy extends AbstractFileSliceStrategy {
    @Override
    public FileTypeEnum getFileType() {
        return FileTypeEnum.DOCX;
    }

    @Override
    public List<SliceInfo> split(String fileName) {
        StorageService storageService = StorageServiceFactory.getDefaultStorageService();
        File file = storageService.getFileByFileName(fileName);
        List<SliceInfo> sliceList = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            XWPFDocument document = new XWPFDocument(inputStream);
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            StringBuilder content = new StringBuilder();
            for (int i = 0; i < paragraphs.size(); i++) {
                XWPFParagraph paragraph = paragraphs.get(i);
                String text = paragraph.getText();
                content.append(StrUtil.isBlank(text) ? "" : text);
                if (content.length() >= MAX_SLICE_SIZE || i == paragraphs.size() - 1) {
                    sliceList.add(SliceInfo.builder().content(content.toString()).build());
                    content = new StringBuilder();
                }
            }

            document.close();
        } catch (IOException e) {
            log.error("slice docx file error", e);
            sliceList.add(SliceInfo.builder().content("文件解析失败").build());
        }
        return sliceList;
    }
}