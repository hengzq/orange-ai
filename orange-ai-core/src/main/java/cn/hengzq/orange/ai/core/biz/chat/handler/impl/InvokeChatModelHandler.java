package cn.hengzq.orange.ai.core.biz.chat.handler.impl;

import cn.hengzq.orange.ai.common.biz.agent.vo.AgentVO;
import cn.hengzq.orange.ai.common.biz.chat.constant.ConverstationEventEnum;
import cn.hengzq.orange.ai.common.biz.chat.constant.MessageTypeEnum;
import cn.hengzq.orange.ai.common.biz.chat.vo.param.CompletionsResponse;
import cn.hengzq.orange.ai.common.biz.session.vo.param.AddSessionMessageParam;
import cn.hengzq.orange.ai.core.biz.chat.handler.AbstractChatHandler;
import cn.hengzq.orange.ai.core.biz.chat.handler.ChatContext;
import cn.hengzq.orange.ai.core.biz.session.service.SessionMessageService;
import cn.hengzq.orange.common.result.ResultWrapper;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class InvokeChatModelHandler extends AbstractChatHandler {

    private final SessionMessageService sessionMessageService;

    private final static String DEFAULT_SYSTEM_PROMPT = "你好！我是小橙子，一款基于大语言模型与检索增强生成（RAG）技术的智能问答助手。我能够根据您提供的知识库内容，快速理解上下文，并为您提供精准、专业且详细的解答。无论是复杂问题还是具体需求，我都会尽力帮助您找到最佳答案。请随时告诉我您的问题，我将全力以赴为您服务！";


    @Override
    protected void before(ChatContext context) {

    }

    @Override
    protected void execute(ChatContext context) {
        SseEmitter emitter = context.getEmitter();
        AgentVO agent = context.getAgent();

        ChatClient.Builder chatClientBuilder = ChatClient.builder(context.getChatModel())
                .defaultSystem(StrUtil.isNotBlank(agent.getSystemPrompt()) ? agent.getSystemPrompt() : DEFAULT_SYSTEM_PROMPT);
        if (CollUtil.isNotEmpty(context.getAdvisors())) {
            chatClientBuilder.defaultAdvisors(context.getAdvisors());
        }

        String mainThreadName = Thread.currentThread().getName();
        StringBuilder replyContent = new StringBuilder();

        Prompt prompt = new Prompt(context.getPrompt(), ChatOptions.builder()
                .model(context.getModel().getModelName())
                .temperature(context.getAgent().getModelConfig().getTemperature())
                .build());

        chatClientBuilder.build().prompt(prompt).stream()
                .chatResponse()
                .takeWhile(chatResponse -> Objects.nonNull(chatResponse) && Objects.nonNull(chatResponse.getResult())
                        && Objects.nonNull(chatResponse.getResult().getOutput()))
                .subscribe(
                        response -> {
                            if (log.isDebugEnabled()) {
                                log.debug("response: {}", response);
                            }
                            Thread.currentThread().setName(mainThreadName);
                            Usage usage = response.getMetadata().getUsage();
                            String content = response.getResult().getOutput().getText();
                            replyContent.append(content);
                            String finishReason = response.getResult().getMetadata().getFinishReason();
                            ConverstationEventEnum event = "STOP".equalsIgnoreCase(finishReason) ? ConverstationEventEnum.FINISHED : ConverstationEventEnum.REPLY;
                            try {
                                emitter.send(ResultWrapper.ok(CompletionsResponse.builder()
                                        .sessionId(context.getSessionId())
                                        .event(event)
                                        .content(content)
                                        .build()));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            if (ConverstationEventEnum.FINISHED.equals(event)) {
                                emitter.complete();
                            }
                        },
                        error -> {
                            log.error("response error: {}", error.getMessage());
                            emitter.complete();
                        },
                        () -> {
                            // 保存会话结果
                            this.saveAssistantMessage(context, replyContent.toString());
                        }
                );
    }

    private void saveAssistantMessage(ChatContext context, String replyContent) {
        sessionMessageService.add(AddSessionMessageParam.builder()
                .parentId(context.getQuestionId())
                .sessionId(context.getSessionId())
                .role(MessageTypeEnum.ASSISTANT)
                .content(replyContent).build());
    }

    @Override
    protected void after(ChatContext context) {

    }


}
