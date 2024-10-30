package cn.hengzq.orange.ai.core.biz.image.controller;

import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hengzq.orange.ai.core.biz.image.service.TextToImageService;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.common.vo.image.TextToImageVO;
import cn.hengzq.orange.ai.common.vo.image.param.GenerateImageParam;
import cn.hengzq.orange.ai.common.vo.image.param.TextToImageListParam;
import cn.hengzq.orange.ai.common.vo.image.param.TextToImagePageParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "AI - 文生图管理")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/text-to-image")
public class TextToImageController {

    private final TextToImageService textToImageService;

    @Operation(summary = "根据文本生成图片")
    @PostMapping("/generate")
    public Result<TextToImageVO> generate(@RequestBody GenerateImageParam param) {
        TextToImageVO imageVO = textToImageService.generate(param);
        return ResultWrapper.ok(imageVO);
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:text-to-image:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<TextToImageVO>> page(@RequestBody TextToImagePageParam param) {
        PageDTO<TextToImageVO> result = textToImageService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:text-to-image:list", description = "返回所有的数据")
    public Result<List<TextToImageVO>> list(@RequestBody TextToImageListParam param) {
        List<TextToImageVO> list = textToImageService.list(param);
        return ResultWrapper.ok(list);
    }

}
