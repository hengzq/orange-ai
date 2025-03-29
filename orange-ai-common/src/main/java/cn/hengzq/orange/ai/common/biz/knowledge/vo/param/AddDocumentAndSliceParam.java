package cn.hengzq.orange.ai.common.biz.knowledge.vo.param;

import cn.hengzq.orange.ai.common.biz.knowledge.constant.KnowledgeErrorCode;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.FileInfo;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.SliceInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hengzq
 */
@Data
@Schema(description = "文档管理 - 添加文档和切片参数")
public class AddDocumentAndSliceParam implements Serializable {

    @NotNull(message = KnowledgeErrorCode.KNOWLEDGE_BASE_ID_CANNOT_NULL_KEY)
    @Schema(description = "知识库ID")
     private String baseId;

    @Schema(description = "文档列表信息")
    private List<DocumentInfo> documentList;


    @Data
  public   static class DocumentInfo implements Serializable {
        @Schema(description = "文件信息")
        private FileInfo fileInfo;

        @Schema(description = "切片相关信息")
        private List<SliceInfo> sliceList;
    }
}
