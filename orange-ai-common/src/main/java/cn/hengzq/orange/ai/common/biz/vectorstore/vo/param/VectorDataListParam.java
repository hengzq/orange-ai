package cn.hengzq.orange.ai.common.biz.vectorstore.vo.param;

import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hengzq
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI - 向量数据管理 - 向量列表数据查询参数")
public class VectorDataListParam implements Serializable {

    @Schema(description = "向量数据库", defaultValue = "MILVUS")
    private VectorDatabaseEnum vectorDatabase = VectorDatabaseEnum.MILVUS;

    @Schema(description = "嵌入式模型供应商", defaultValue = "ALI_BAI_LIAN")
    private PlatformEnum embeddingModelPlatform = PlatformEnum.ALI_BAI_LIAN;

    @Schema(description = "文本内容")
    private String text;
}
