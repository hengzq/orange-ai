package cn.hengzq.orange.ai.core.biz.session.controller;


import cn.hengzq.orange.ai.common.biz.session.vo.SessionVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionListParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionPageParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.UpdateSessionParam;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.session.service.SessionService;
import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hengzq
 */
@Tag(name = "AI - 聊天会话管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/session")
public class SessionController {

    private final SessionService sessionService;

    @Operation(summary = "新建", operationId = "orange-ai:session:add")
    @PostMapping
    public Result<Long> add(@RequestBody @Validated AddSessionParam request) {
        return ResultWrapper.ok(sessionService.add(request));
    }

    @Operation(summary = "根据ID删除", operationId = "orange-ai:session:delete")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteById(@PathVariable("id") Long id) {
        return ResultWrapper.ok(sessionService.deleteById(id));
    }

    @Operation(summary = "根据ID更新", operationId = "orange-ai:session:update")
    @PutMapping("/{id}")
    public Result<Boolean> updateById(@PathVariable("id") Long id, @RequestBody @Validated UpdateSessionParam param) {
        return ResultWrapper.ok(sessionService.updateById(id, param));
    }

    @Operation(summary = "根据ID查询", operationId = "orange-ai:session:get")
    @GetMapping("/{id}")
    public Result<SessionVO> getById(@PathVariable("id") Long id) {
        return ResultWrapper.ok(sessionService.getById(id));
    }

    @Operation(summary = "分页查询", operationId = "orange-ai:session:page")
    @PostMapping(value = "/page")
    public Result<PageDTO<SessionVO>> page(@RequestBody SessionPageParam param) {
        PageDTO<SessionVO> result = sessionService.page(param);
        return ResultWrapper.ok(result);
    }

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:session:list", description = "返回所有的数据")
    public Result<List<SessionVO>> list(@RequestBody SessionListParam param) {
        List<SessionVO> list = sessionService.list(param);
        return ResultWrapper.ok(list);
    }
}
