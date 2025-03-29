package cn.hengzq.orange.ai.core.biz.chat.handler.impl;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.model.constant.AIModelErrorCode;
import cn.hengzq.orange.ai.common.biz.model.vo.ModelVO;
import cn.hengzq.orange.ai.core.biz.chat.handler.AbstractChatHandler;
import cn.hengzq.orange.ai.core.biz.chat.handler.ChatContext;
import cn.hengzq.orange.ai.core.biz.chat.service.ChatModelServiceFactory;
import cn.hengzq.orange.ai.core.biz.model.service.ModelService;
import cn.hengzq.orange.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 加载模型处理器
 */
@Slf4j
@Component
@AllArgsConstructor
public class LoadModelHandler extends AbstractChatHandler {

    private final ChatModelServiceFactory chatModelServiceFactory;

    private final ModelService modelService;

    @Override
    protected void before(ChatContext context) {
        log.info("before chat context:{}", context);
    }

    @Override
    protected void execute(ChatContext context) {
        String modelId = context.getModelId();
        if (Objects.nonNull(context.getAgent())) {
            modelId = context.getAgent().getModelId();
        }

        ModelVO model = modelService.getById(modelId);
        if (Objects.isNull(model)) {
            log.error("模型不存在 modelId: {}", modelId);
            throw new ServiceException(AIModelErrorCode.MODEL_DATA_NOT_EXIST);
        }
        ChatModelService chatModelService = chatModelServiceFactory.getChatModelService(model.getPlatform());
        ChatModel chatModel = chatModelService.getOrCreateChatModel(model);
        if (Objects.isNull(chatModel)) {
            log.error("获取或创建模型失败 modelId: {}", modelId);
            throw new ServiceException(AIModelErrorCode.CHAT_MODEL_CREATE_ERROR);
        }
        context.setModelId(modelId);
        context.setModel(model);
        context.setChatModel(chatModel);
    }

    @Override
    protected void after(ChatContext context) {
        log.info("after chat context:{}", context);
    }


}
