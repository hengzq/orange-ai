package cn.hengzq.orange.ai.core.biz.session.controller;


import cn.hengzq.orange.ai.common.biz.session.vo.SessionMessageVO;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionMessageListParam;
import cn.hengzq.orange.ai.common.biz.session.vo.param.SessionMessageRateParam;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.core.biz.session.service.SessionMessageService;
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
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/session-message")
public class SessionMessageController {

    private final SessionMessageService sessionMessageService;

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:session-message:list", description = "返回所有的数据")
    public Result<List<SessionMessageVO>> list(@RequestBody SessionMessageListParam param) {
        List<SessionMessageVO> list = sessionMessageService.list(param);
        return ResultWrapper.ok(list);
    }

    @Operation(summary = "评价", operationId = "orange-ai:session-message:rate")
    @PutMapping("/rate/{id}")
    public Result<Boolean> rateById(@PathVariable("id") Long id, @RequestBody @Validated SessionMessageRateParam param) {
        return ResultWrapper.ok(sessionMessageService.rateById(id, param));
    }
}
