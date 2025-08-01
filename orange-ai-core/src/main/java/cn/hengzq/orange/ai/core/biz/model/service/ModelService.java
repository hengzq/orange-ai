package cn.hengzq.orange.ai.core.biz.model.service;


import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.model.vo.param.AddModelParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.ModelListParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.ModelPageParam;
import cn.hengzq.orange.ai.common.biz.model.vo.param.UpdateModelParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;
import java.util.Map;

/**
 * @author hengzq
 */
public interface ModelService {

    String add(AddModelParam request);

    Boolean removeById(String id);

    Boolean updateById(String id, UpdateModelParam request);

    Boolean updateEnabledById(String id, boolean enabled);

    ModelVO getById(String id);

    List<ModelVO> list(ModelListParam query);

    PageDTO<ModelVO> page(ModelPageParam param);

    Map<String, ModelVO> mapModelByIds(List<String> modelIds);

}
