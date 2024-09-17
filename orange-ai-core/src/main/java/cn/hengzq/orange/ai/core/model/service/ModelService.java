package cn.hengzq.orange.ai.core.model.service;


import cn.hengzq.orange.ai.common.vo.model.ModelVO;
import cn.hengzq.orange.ai.common.vo.model.param.AddModelParam;
import cn.hengzq.orange.ai.common.vo.model.param.ModelListParam;
import cn.hengzq.orange.ai.common.vo.model.param.ModelPageParam;
import cn.hengzq.orange.ai.common.vo.model.param.UpdateModelParam;
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
