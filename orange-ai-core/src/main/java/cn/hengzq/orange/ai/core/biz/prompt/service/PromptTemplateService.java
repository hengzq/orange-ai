package cn.hengzq.orange.ai.core.biz.prompt.service;


import cn.hengzq.orange.ai.common.biz.prompt.vo.PromptTemplateVO;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.AddPromptTemplateParam;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.PromptTemplateListParam;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.PromptTemplatePageParam;
import cn.hengzq.orange.ai.common.biz.prompt.vo.param.UpdatePromptTemplateParam;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;
import java.util.Map;

/**
 * @author hengzq
 */
public interface PromptTemplateService {

    String add(AddPromptTemplateParam param);

    Boolean removeById(String id);

    Boolean updateById(String id, UpdatePromptTemplateParam param);

    PromptTemplateVO getById(String id);

    List<PromptTemplateVO> list(PromptTemplateListParam param);

    PageDTO<PromptTemplateVO> page(PromptTemplatePageParam param);

    Map<String, PromptTemplateVO> mapModelByIds(List<String> modelIds);
}
