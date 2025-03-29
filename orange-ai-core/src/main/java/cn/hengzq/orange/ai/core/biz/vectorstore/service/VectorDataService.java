package cn.hengzq.orange.ai.core.biz.vectorstore.service;

import cn.hengzq.orange.ai.common.biz.vectorstore.vo.param.AddVectorDataParam;
import cn.hengzq.orange.ai.common.biz.vectorstore.vo.param.VectorDataListParam;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

public interface VectorDataService {

    String add(AddVectorDataParam param);

    List<Document> list(VectorDataListParam param);

}
