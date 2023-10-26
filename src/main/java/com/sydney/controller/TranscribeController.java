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



    //set params of model
    @PostMapping("/kaldi/set/model/")
    public Result setModel(@RequestBody Model model) throws IOException {
        return transcribeService.setModelParamKaldi(model);
    }

    //set params before make transcribe
    @GetMapping("/kaldi/upload")
    public Result upload(@RequestParam("filePath") String filePath) throws IOException {
        return transcribeService.uploadAudio(filePath);
    }

    //begin to trans Kaldi
    @GetMapping("/kaldi/transcribe")
    public Result transcribe() {
        return transcribeService.transcribe();
    }

    //begin to trans HFT
    @GetMapping("/hft/transcribe")
    public Result transcribeHFT() {
        return transcribeService.transcribe();
    }

    //use model trained by htf engine to transcribe audio
    @GetMapping("/hft/upload")
    public  Result transcribeByHft(@RequestParam("filePath") String filePath) throws IOException {
        return transcribeService.uploadAudio(filePath);
    }

    //set params before make transcribe
    @PostMapping("/hft/set/model")
    public  Result transcribeByHft(@RequestBody Model model) throws IOException {
        return transcribeService.setModelParamHFT(model);
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
    public Result uploadModel(@RequestParam("modelPath") String modelPath) throws IOException {
        return transcribeService.uploadModel(modelPath);
    }

}
