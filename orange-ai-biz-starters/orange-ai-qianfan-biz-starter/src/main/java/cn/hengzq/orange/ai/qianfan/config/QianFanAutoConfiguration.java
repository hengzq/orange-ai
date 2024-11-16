package cn.hengzq.orange.ai.qianfan.config;

import cn.hengzq.orange.ai.common.biz.chat.service.ChatModelService;
import cn.hengzq.orange.ai.common.biz.image.service.ImageModelService;
import cn.hengzq.orange.ai.qianfan.chat.QianFanChatModelServiceImpl;
import cn.hengzq.orange.ai.qianfan.image.QianFanImageModelServiceImpl;
import org.springframework.ai.qianfan.QianFanChatModel;
import org.springframework.ai.qianfan.QianFanImageModel;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 百度智能云-千帆ModelBuilder 配置中心
 */
@AutoConfiguration
public class QianFanAutoConfiguration {

    @Bean
    public ChatModelService qianFanChatModelService(QianFanChatModel qianFanChatModel) {
        return new QianFanChatModelServiceImpl(qianFanChatModel);
    }

    @Bean
    public ImageModelService qianFanImageModelService(QianFanImageModel qianFanImageModel) {
        return new QianFanImageModelServiceImpl(qianFanImageModel);
    }

}
