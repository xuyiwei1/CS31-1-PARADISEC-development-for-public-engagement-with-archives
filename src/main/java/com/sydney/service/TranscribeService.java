package com.sydney.service;

import com.sydney.pojo.Model;
import com.sydney.pojo.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Yiwei Xu
 * @version 1.0
 * @description TODO
 * @date 2023/10/4 9:53
 */
public interface TranscribeService {
    public Result getModels();
    public Result uploadAudio(String filePath) throws IOException;

    //Result uploadAudioMakeTranscribeByHft(MultipartFile file) throws IOException;

    Result getTranscribeResult();

    Result getTranscribeStatus();

    Result getTranscribeResultElan();

    Result uploadModel(MultipartFile model) throws IOException;

    Result setModelParamKaldi(Model model);

    Result setModelParamHFT(Model model);

    Result transcribe();
}
