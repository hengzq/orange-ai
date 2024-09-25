package cn.hengzq.orange.ai.common.service.image;

import cn.hengzq.orange.ai.common.constant.PlatformEnum;
import cn.hengzq.orange.ai.common.vo.chat.ConversationReplyVO;
import cn.hengzq.orange.ai.common.vo.chat.param.ConversationParam;
import cn.hengzq.orange.ai.common.vo.image.param.GenerateImageParam;
import cn.hengzq.orange.common.result.Result;
import org.springframework.ai.image.ImageResponse;
import reactor.core.publisher.Flux;

public interface ImageModelService {

    PlatformEnum getPlatform();

    ImageResponse textToImage(GenerateImageParam param);
}
