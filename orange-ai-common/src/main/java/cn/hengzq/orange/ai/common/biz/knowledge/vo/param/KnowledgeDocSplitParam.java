package cn.hengzq.orange.ai.common.biz.knowledge.vo.param;

import cn.hengzq.orange.ai.common.biz.knowledge.vo.FileInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hengzq
 */
@Data
@Schema(description = "知识库管理 - 知识文档 - 切割参数")
public class KnowledgeDocSplitParam implements Serializable {

    @Schema(description = "切片标识符")
    private List<String> sliceIdentifierList;
    

    @Schema(description = "文件名称")
    private List<FileInfo> fileList;

}
