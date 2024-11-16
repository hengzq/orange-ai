package cn.hengzq.orange.ai.core.biz.model.service;


import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.model.vo.param.AddModelParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.ModelListParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.ModelPageParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.UpdateModelParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;

/**
 * @author hengzq
 */
public interface ModelService {

    Long add(AddModelParam request);

    Boolean removeById(Long id);

    Boolean updateById(Long id, UpdateModelParam request);

    ModelVO getById(Long id);

    List<ModelVO> list(ModelListParam query);

    PageDTO<ModelVO> page(ModelPageParam param);
}
