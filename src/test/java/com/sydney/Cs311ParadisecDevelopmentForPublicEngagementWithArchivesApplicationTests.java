package com.sydney;

import com.alibaba.fastjson.JSON;
import com.sydney.utils.FileUtil;
import com.sydney.utils.HttpClientUtil;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class Cs311ParadisecDevelopmentForPublicEngagementWithArchivesApplicationTests {

    @Value("${elpis.url}")
    private String url;

    @Test
    void testGetRequest() {
        String result = HttpClientUtil.getRequestNoParam("http://192.168.200.100:5001/api/dataset/list");
        System.out.println(result);
    }

    @Test
    void testdoPostWithParam() throws UnsupportedEncodingException {
        HashMap<String, String> params = new HashMap<>();
        params.put("name","new-recordings");
        String param = JSON.toJSONString(params);
        String result = HttpClientUtil.postWithRequestBody("http://192.168.200.100:5001/api/dataset/new", param);
        System.out.println(result);
    }

    @Test
    void testUploadFile() throws IOException {
        byte[] bytes = FileUtil.toByteArray("D:\\student\\Aus\\comp5703\\nafsan\\20170726-AK-005.eaf");
        byte[] res = HttpClientUtil.post("http://192.168.200.100:5001/api/dataset/files", bytes, null);
        System.out.println(res);
    }

    /***
     * @description TODO
     * 选择一个模型，然后使用该模型对语音文件进行转录
     * @return void
     * @author Yiwei Xu
     * @date 2023/9/27 15:05
    */
    @Test
    void testUsingModelTrainAudio() throws Exception {
        //设置引擎名称
        String engineName = "kaldi";
        Map<String,String> engineMap = new HashMap<>();
        engineMap.put("engine_name",engineName);
        HttpClientUtil.postWithRequestBody(url+"/api/config/engine/load",engineMap.toString());
        //设置模型
        Map<String,String> modelMap = new HashMap<>();
        modelMap.put("name","m");
        HttpClientUtil.postWithRequestBody(url+"/api/model/load",modelMap.toString());
        //设置数据集
        Map<String,String> dataSetMap = new HashMap<>();
        dataSetMap.put("name","ds3");
        HttpClientUtil.postWithRequestBody(url+"/api/dataset/load",dataSetMap.toString());
        //设置字典
        Map<String,String> pronDictMap = new HashMap<>();
        pronDictMap.put("name","pd");
        HttpClientUtil.postWithRequestBody(url+"/api/pron-dict/load",pronDictMap.toString());
        //上传需要转录的语音文件
        HttpClientUtil.fileUpload(url+"/api/transcription/new",FileUtil.toByteArray("D:\\student\\Aus\\comp5703\\nafsan\\20170726-AK-005.eaf"));
        //开始转录
        HttpClientUtil.getRequestNoParam(url+"/api/transcription/transcribe");
    }
}
