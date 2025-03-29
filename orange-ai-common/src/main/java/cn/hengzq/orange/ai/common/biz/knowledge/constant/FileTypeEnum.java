package cn.hengzq.orange.ai.common.biz.knowledge.constant;

/**
 * 文件类型
 */
public enum FileTypeEnum {
    DOCX,
    PDF,
    TXT,
    XLSX,
    PPTX,
    ;


    public static FileTypeEnum getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}
