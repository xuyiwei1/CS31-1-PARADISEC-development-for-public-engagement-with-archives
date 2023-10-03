package com.sydney.service.impl;

import com.sydney.pojo.Model;
import com.sydney.pojo.Result;
import com.sydney.service.TranscribeService;
import com.sydney.utils.FileUtil;
import com.sydney.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yiwei Xu
 * @version 1.0
 * @description TODO
 * @date 2023/10/4 9:49
 */
@Service
public class TranscribeServiceImpl implements TranscribeService {

    @Value("${elpis.url}")
    private String url;

    //get trained models' info from elpis
    public Result getModels() {
        String models = HttpClientUtil.getRequestNoParam(url + "/api/model/list");
        return Result.ok(models);
    }

    //upload audio to make transcribe
    public Result uploadAudioMakeTranscribe(Model model, MultipartFile file) throws IOException {
        //非空校验
        if(model == null) {
            return Result.fail("the model can not be null");
        }
        if(model.getModelName() == null || StringUtils.isEmpty(model.getModelName())) {
            return Result.fail("model name can not be null or empty");
        }
        if(model.getEngineName() == null || StringUtils.isEmpty(model.getEngineName())) {
            return Result.fail("model name can not be null or empty");
        }
        if(model.getDataSetName() == null || StringUtils.isEmpty(model.getDataSetName())) {
            return Result.fail("model name can not be null or empty");
        }
        if(model.getPronDictMapName() == null || StringUtils.isEmpty(model.getPronDictMapName())) {
            return Result.fail("model name can not be null or empty");
        }
        if(file == null || file.isEmpty()) {
            return Result.fail("audio can not be null or empty");
        }
        //设置引擎名称
        String engineName = "kaldi";
        Map<String,String> engineMap = new HashMap<>();
        engineMap.put("engine_name",model.getEngineName());
        HttpClientUtil.postWithRequestBody(url+"/api/config/engine/load",engineMap.toString());
        //设置模型
        Map<String,String> modelMap = new HashMap<>();
        modelMap.put("name",model.getModelName());
        HttpClientUtil.postWithRequestBody(url+"/api/model/load",modelMap.toString());
        //设置数据集
        Map<String,String> dataSetMap = new HashMap<>();
        dataSetMap.put("name",model.getDataSetName());
        HttpClientUtil.postWithRequestBody(url+"/api/dataset/load",dataSetMap.toString());
        //设置字典
        Map<String,String> pronDictMap = new HashMap<>();
        pronDictMap.put("name",model.getPronDictMapName());
        HttpClientUtil.postWithRequestBody(url+"/api/pron-dict/load",pronDictMap.toString());
        //二进制音频文件
        byte[] bytes = file.getBytes();
        //上传需要转录的语音文件
        HttpClientUtil.fileUpload(url+"/api/transcription/new", bytes);
        //开始转录
        HttpClientUtil.getRequestNoParam(url+"/api/transcription/transcribe");
        return Result.ok("Please wait for transcribing");
    }
}
