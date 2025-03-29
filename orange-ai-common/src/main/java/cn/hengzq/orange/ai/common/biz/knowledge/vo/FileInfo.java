package cn.hengzq.orange.ai.common.biz.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "文件信息")
public class FileInfo implements Serializable {

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "文件大小")
    private Long fileSize;
}
