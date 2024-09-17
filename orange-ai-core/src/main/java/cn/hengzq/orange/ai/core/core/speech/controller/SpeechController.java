package cn.hengzq.orange.ai.core.core.speech.controller;

import cn.hengzq.orange.ai.common.constant.AIConstant;
import com.alibaba.cloud.ai.tongyi.audio.speech.TongYiAudioSpeechOptions;
import com.alibaba.cloud.ai.tongyi.audio.speech.api.SpeechModel;
import com.alibaba.cloud.ai.tongyi.audio.speech.api.SpeechPrompt;
import com.alibaba.dashscope.audio.tts.SpeechSynthesisAudioFormat;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "模型-语音合成")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AIConstant.V1_0_URL_PREFIX + "/speech")
public class SpeechController {
    private final SpeechModel speechClient;

    @GetMapping("/audio/speech")
    public String genAudio(@RequestParam(value = "prompt",
            defaultValue = "你好，Spring Cloud Alibaba AI 框架！") String prompt) {
        SpeechPrompt speechPrompt = new SpeechPrompt(prompt, TongYiAudioSpeechOptions.builder()
                .withModel("sambert-cindy-v1").build());
        var resWAV = speechClient.call(speechPrompt).getResult().getOutput();
        return save(resWAV, SpeechSynthesisAudioFormat.WAV.getValue());
    }

    private String save(ByteBuffer audio, String type) {

        String currentPath = System.getProperty("user.dir");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-HH-mm-ss");
        String fileName = currentPath + File.separator + now.format(formatter) + "." + type;
        File file = new File(fileName);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(audio.array());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }

}
