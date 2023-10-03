package com.sydney.controller;

import com.sydney.pojo.Model;
import com.sydney.pojo.Result;
import com.sydney.service.TranscribeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Yiwei Xu
 * @version 1.0
 * @description TODO
 * @date 2023/10/4 9:46
 */
@RestController
public class TranscribeController {
    @Resource
    private TranscribeService transcribeService;

    //Get trained model from elpis
    @GetMapping("/list/models")
    public Result getModels() {
        return transcribeService.getModels();
    }

    //begin to transcribe an audio
    @PostMapping("/transcribe")
    public Result transcribe(@RequestBody Model model, @RequestPart MultipartFile file) throws IOException {
        return transcribeService.uploadAudioMakeTranscribe(model,file);
    }
}
