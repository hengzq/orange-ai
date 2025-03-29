package cn.hengzq.orange.ai.core.biz.chat.handler;

import cn.hengzq.orange.ai.common.biz.agent.vo.AgentVO;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.common.biz.session.constant.SessionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatContext {

    @Schema(description = "对话类型")
    private SessionTypeEnum sessionType;

    @Schema(description = "回话ID")
    private String sessionId;

    @Schema(description = "提示词")
    private String prompt;

    @Schema(description = "智能体ID 用于chatType为AGENT时必填")
    private String agentId;

    private AgentVO agent;

    private String modelId;

    private ModelVO model;

    private ChatModel chatModel;

    /**
     * SSE 发射器，用于向前端推送数据
     */
    private SseEmitter emitter;

    @Schema(description = "用户问题ID")
    private String questionId;

    private List<Advisor> advisors;
}
