package cn.hengzq.orange.ai.common.biz.vectorstore.constant;

import cn.hengzq.orange.common.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum VectorDatabaseEnum implements BaseEnum<String> {

    MILVUS("Milvus");

    private final String description;

    VectorDatabaseEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
