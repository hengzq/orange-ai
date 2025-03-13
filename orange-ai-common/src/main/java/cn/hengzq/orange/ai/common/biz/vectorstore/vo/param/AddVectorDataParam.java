package cn.hengzq.orange.ai.common.biz.vectorstore.vo.param;

import cn.hengzq.orange.ai.common.biz.vectorstore.constant.VectorDatabaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "AI - 向量数据管理 - 添加向量数据参数")
public class AddVectorDataParam implements Serializable {

    @Schema(description = "向量数据库", defaultValue = "MILVUS")
    private VectorDatabaseEnum vectorDatabase = VectorDatabaseEnum.MILVUS;

    @Schema(description = "文本内容")
    private List<String> texts;
}
