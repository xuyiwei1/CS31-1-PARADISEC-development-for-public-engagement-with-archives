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
@CrossOrigin(origins = "*")
public class TranscribeController {
    @Resource
    private TranscribeService transcribeService;

    //Get trained model from elpis
    @GetMapping("/list/models")
    public Result getModels() {
        return transcribeService.getModels();
    }

    //begin to transcribe an audio (use Kalid engine)
    @PostMapping("/kalid/transcribe")
    public Result transcribe(@RequestBody Model model, @RequestPart("file") MultipartFile file) throws IOException {
        return transcribeService.uploadAudioMakeTranscribe(model,file);
    }

    //use model trained by htf engine to transcribe audio
    @PostMapping("/hft/transcibe")
    public  Result transcribeByHft(@RequestBody Model model, @RequestPart MultipartFile file) throws IOException {
        return transcribeService.uploadAudioMakeTranscribeByHft(model,file);
    }

    //get transcribe result in text format
    @GetMapping("/transcribe/result/text")
    public Result getTranscribeResult() {
        return transcribeService.getTranscribeResult();
    }

    //get transcribe result in elan format
    @GetMapping("/transcribe/result/elan")
    public Result getTranscribeResultElan() {
        return transcribeService.getTranscribeResultElan();
    }

    //get transcribe status
    @GetMapping("/transcribe/status")
    public Result getTranscribeStatus() {
        return transcribeService.getTranscribeStatus();
    }

    //upload a model to elpis
    @PostMapping("/upload/model")
    public Result uploadModel(@RequestPart("file") MultipartFile file) throws IOException {
        return transcribeService.uploadModel(file);
    }

}
