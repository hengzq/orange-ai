package cn.hengzq.orange.ai.core.biz.knowledge.service;


import cn.hengzq.orange.ai.common.biz.knowledge.vo.ChunkVO;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.AddDocSliceParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeDocSliceListParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.KnowledgeDocSlicePageParam;
import cn.hengzq.orange.ai.common.biz.knowledge.vo.param.UpdateDocSliceParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;

/**
 * @author hengzq
 */
public interface ChunkService {

    String add(AddDocSliceParam param);

    Boolean batchAdd(List<ChunkVO> chunks);

    Boolean deleteById(String id);

    Boolean deleteByDocId(String docId);

    Boolean deleteByBaseId(String baseId);

    Boolean updateById(String id, UpdateDocSliceParam request);

    ChunkVO getById(String id);

    List<ChunkVO> list(KnowledgeDocSliceListParam param);

    PageDTO<ChunkVO> page(KnowledgeDocSlicePageParam param);

}
