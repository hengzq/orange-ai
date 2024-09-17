package cn.hengzq.orange.ai.core.core.chat.controller;


import cn.hengzq.orange.ai.core.core.chat.service.ChatSessionRecordService;
import cn.hengzq.orange.ai.common.constant.AIConstant;
import cn.hengzq.orange.ai.common.vo.chat.ChatSessionRecordVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ChatSessionLogListParam;
import cn.hengzq.orange.common.result.Result;
import cn.hengzq.orange.common.result.ResultWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hengzq
 */
@Tag(name = "聊天会话 - 记录管理")
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
}
