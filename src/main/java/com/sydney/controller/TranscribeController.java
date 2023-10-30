package com.sydney.controller;


import cn.hutool.http.HttpUtil;
import com.sydney.pojo.Model;
import com.sydney.pojo.Result;
import com.sydney.service.TranscribeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


/**
 * @author Yiwei Xu
 * @version 1.0
 * @description TODO
 * @date 2023/10/4 9:46
 */
@RestController
//@CrossOrigin
public class TranscribeController {
    @Resource
    private TranscribeService transcribeService;

    @Value("${elpis.url}")
    private String url;

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
    public Result transcribeByHft(@RequestParam("filePath") String filePath) throws IOException {
        return transcribeService.uploadAudio(filePath);
    }

    @PostMapping("/test")
    public Result test(@RequestParam("files") MultipartFile file) throws IOException {
        HashMap<String, Object> paramMap = new HashMap<>();
        File file1 = this.MultipartFileToFile(file);
        paramMap.put("file", file1);
        String result = HttpUtil.post(url + "/api/model/upload", paramMap);
        System.out.println(result);
        return null;
    }


    //set params before make transcribe
    @PostMapping("/hft/set/model")
    public Result transcribeByHft(@RequestBody Model model) throws IOException {
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


    private File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //set params of model
    @PostMapping("/kaldi/set/model2")
    public Result setModel2(@RequestBody Model model) throws IOException {
        return transcribeService.setModelParamKaldi(model);
    }

    //set params before make transcribe
    @PostMapping("/kaldi/upload2")
    public Result upload2(@RequestParam("file") MultipartFile file) throws IOException {
        HashMap<String, Object> paramMap = new HashMap<>();
        File file1 = this.MultipartFileToFile(file);
        paramMap.put("file", file1);
        String result= HttpUtil.post(url + "/api/transcription/new", paramMap);
        System.out.println(result);
        return Result.ok(result);
    }

    //begin to trans Kaldi
    @GetMapping("/kaldi/transcribe2")
    public Result transcribe2() {
        return transcribeService.transcribe();
    }

    //set params before make transcribe
    @PostMapping("/hft/set/model2")
    public Result transcribeByHft2(@RequestBody Model model) throws IOException {
        return transcribeService.setModelParamHFT(model);
    }

    //use model trained by htf engine to transcribe audio
    @PostMapping("/hft/upload2")
    public Result transcribeByHft2(@RequestParam("file") MultipartFile file) throws IOException {
        HashMap<String, Object> paramMap = new HashMap<>();
        File file1 = this.MultipartFileToFile(file);
        paramMap.put("file", file1);
        String result= HttpUtil.post(url + "/api/transcription/new", paramMap);
        System.out.println(result);
        return Result.ok(result);
    }


    //begin to trans HFT
    @GetMapping("/hft/transcribe2")
    public Result transcribeHFT2() {
        return transcribeService.transcribe();
    }

}

