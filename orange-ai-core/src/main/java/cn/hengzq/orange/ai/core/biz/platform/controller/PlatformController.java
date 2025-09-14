package cn.hengzq.orange.ai.core.biz.platform.controller;


import cn.hengzq.orange.ai.common.biz.model.dto.ModelType;
import cn.hengzq.orange.ai.common.biz.platform.vo.PlatformVO;
import cn.hengzq.orange.ai.common.biz.platform.vo.param.PlatformListParam;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.common.constant.ModelTypeEnum;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.embedding.service.EmbeddingModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.image.service.ImageModelServiceFactory;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hengzq
 */
@Tag(name = "AI - 平台管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/platform")
public class PlatformController {

    private final ChatModelServiceFactory chatModelServiceFactory;

    private final EmbeddingModelServiceFactory embeddingModelServiceFactory;

    private final ImageModelServiceFactory imageModelServiceFactory;

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:platform:list", description = "返回所有的数据")
    public Result<List<PlatformVO>> list(@RequestBody PlatformListParam param) {
        List<PlatformEnum> platformList = Arrays.stream(PlatformEnum.values()).toList();
        // 过滤
        if (Objects.nonNull(param.getModelType())) {
            platformList = platformList.stream().filter(item -> item.getModelTypes().contains(param.getModelType())).toList();
        }

        List<PlatformVO> list = platformList.stream()
                .map(item -> new PlatformVO(
                        item.getDescription(),
                        item,
                        item.getSort(),
                        item.getModelTypes().stream().map(type -> new ModelType(type.getDescription(), type)).toList())
                )
                .sorted(Comparator.comparing(PlatformVO::getSort))
                .collect(Collectors.toList());
        return ResultWrapper.ok(list);
    }

    @GetMapping(value = "/list-model/{platform}/{modelType}")
    @Operation(summary = "根据供应商和模型类型，查询支持的模型", operationId = "orange-ai:platform:list", description = "返回所有的数据")
    public Result<List<String>> listModel(@PathVariable("platform") PlatformEnum platform,
                                          @PathVariable("modelType") ModelTypeEnum modelType) {
        List<String> modelList = List.of();
        if (ModelTypeEnum.CHAT.equals(modelType)) {
            modelList = chatModelServiceFactory.getChatModelService(platform).listModel();
        } else if (ModelTypeEnum.EMBEDDING.equals(modelType)) {
            modelList = embeddingModelServiceFactory.getEmbeddingModelService(platform).listModel();
        } else if (ModelTypeEnum.TEXT_TO_IMAGE.equals(modelType)) {
            modelList = imageModelServiceFactory.getImageModelService(platform).listModel();
        }
        return ResultWrapper.ok(modelList);
    }

}
