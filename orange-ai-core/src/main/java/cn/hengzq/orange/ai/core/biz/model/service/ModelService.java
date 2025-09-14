package cn.hengzq.orange.ai.core.biz.model.service;


import cn.hengzq.orange.ai.common.biz.model.dto.ModelResponse;
import cn.hengzq.orange.ai.common.biz.model.dto.param.ModelCreateRequest;
import cn.hengzq.orange.ai.common.biz.model.dto.param.ModelPageRequest;
import cn.hengzq.orange.ai.common.biz.model.dto.param.ModelQueryRequest;
import cn.hengzq.orange.ai.common.biz.model.dto.param.ModelUpdateRequest;
import cn.hengzq.orange.common.dto.PageDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author hengzq
 */
public interface ModelService {

    String createModel(ModelCreateRequest request);

    void deleteModelById(String id);

    void updateModelById(String id, ModelUpdateRequest request);

    void updateEnabledById(String id, boolean enabled);

    Optional<ModelResponse> getModelById(String id);

    ModelResponse getById(String id);

    List<ModelResponse> list(ModelQueryRequest query);

    PageDTO<ModelResponse> page(ModelPageRequest param);

    Map<String, ModelResponse> mapModelByIds(List<String> modelIds);

}
