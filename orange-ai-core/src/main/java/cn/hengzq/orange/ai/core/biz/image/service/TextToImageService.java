package cn.hengzq.orange.ai.core.biz.image.service;

import cn.hengzq.orange.common.dto.PageDTO;
import cn.hengzq.orange.ai.common.biz.image.vo.TextToImageVO;
import cn.hengzq.orange.ai.common.biz.image.vo.param.GenerateImageParam;
import cn.hengzq.orange.ai.common.biz.image.vo.param.TextToImageListParam;
import cn.hengzq.orange.ai.common.biz.image.vo.param.TextToImagePageParam;

import java.util.List;

public interface TextToImageService {

    PageDTO<TextToImageVO> page(TextToImagePageParam param);

    List<TextToImageVO> list(TextToImageListParam param);

    TextToImageVO generate(GenerateImageParam param);
}
