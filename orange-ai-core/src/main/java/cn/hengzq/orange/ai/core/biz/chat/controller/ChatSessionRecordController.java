package cn.hengzq.orange.ai.core.biz.chat.controller;


import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.common.biz.chat.vo.ChatSessionRecordVO;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ChatSessionLogListParam;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.ChatSessionRecordRateParam;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatSessionRecordService;
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
@Tag(name = "AI - 聊天会话记录管理")
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/chat-session-record")
public class ChatSessionRecordController {

    private final ChatSessionRecordService chatSessionRecordService;

    @PostMapping(value = "/list")
    @Operation(summary = "查询所有的数据", operationId = "orange-ai:chat-session-record:list", description = "返回所有的数据")
    public Result<List<ChatSessionRecordVO>> list(@RequestBody ChatSessionLogListParam query) {
        List<ChatSessionRecordVO> list = chatSessionRecordService.list(query);
        return ResultWrapper.ok(list);
    }

    @Operation(summary = "评价", operationId = "orange-ai:chat-session-record:rate")
    @PutMapping("/rate/{id}")
    public Result<Boolean> rateById(@PathVariable("id") Long id, @RequestBody @Validated ChatSessionRecordRateParam param) {
        return ResultWrapper.ok(chatSessionRecordService.rateById(id, param));
    }


}
