package cn.hengzq.orange.ai.core.biz.platform.controller;


import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.vo.model.ModelTypeVO;
import cn.hengzq.orange.ai.common.vo.platform.PlatformVO;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hengzq
 */
@Tag(name = "AI 平台管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/platform")
public class PlatformController {

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:platform:list", description = "返回所有的数据")
    public Result<List<PlatformVO>> list() {
        List<PlatformVO> list = Arrays.stream(PlatformEnum.values())
                .map(item -> new PlatformVO(
                        item.getDescription(),
                        item,
                        item.getModelTypes().stream().map(type -> new ModelTypeVO(type.getDescription(), type)).toList())
                )
                .collect(Collectors.toList());
        return ResultWrapper.ok(list);
    }
}
