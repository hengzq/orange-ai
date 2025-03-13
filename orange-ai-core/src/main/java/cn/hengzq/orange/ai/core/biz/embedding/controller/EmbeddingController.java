package cn.hengzq.orange.ai.core.biz.embedding.controller;

import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.embedding.service.EmbeddingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AI - 嵌入式模型")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/embedding")
public class EmbeddingController {

    private final EmbeddingService embeddingService;


//    @Operation(summary = "将文本批量转换为向量", operationId = "orange-ai:embedding:embed-to-float")
//    @PostMapping("/embed-to-float")
//    public Result<List<float[]>> embedToFloat(@RequestBody EmbedParam param) {
//        List<float[]> floats = embeddingService.embedToFloat(param);
//        return ResultWrapper.ok(floats);
//    }


}
