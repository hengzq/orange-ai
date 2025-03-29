package cn.hengzq.orange.ai.core.biz.vectorstore.controller;

import cn.hengzq.orange.ai.common.biz.vectorstore.vo.param.AddVectorDataParam;
import cn.hengzq.orange.ai.common.biz.vectorstore.vo.param.VectorDataListParam;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.vectorstore.service.VectorDataService;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "AI - 向量数据管理")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/vector-data")
public class VectorDataController {

    private final VectorDataService vectorDataService;

    @Operation(summary = "添加向量数据", operationId = "orange-ai:vector-data:add")
    @PostMapping
    public Result<String> add(@RequestBody @Validated AddVectorDataParam param) {
        return ResultWrapper.ok(vectorDataService.add(param));
    }

    @Operation(summary = "列表查询", operationId = "orange-ai:vector-data:list")
    @PostMapping(value = "/list")
    public Result<List<Document>> list(@RequestBody @Validated VectorDataListParam param) {
        return ResultWrapper.ok(vectorDataService.list(param));
    }

}
