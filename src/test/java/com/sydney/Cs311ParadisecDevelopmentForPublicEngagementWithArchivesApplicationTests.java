package com.sydney;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sydney.pojo.Model;
import com.sydney.pojo.Result;
import com.sydney.service.TranscribeService;
import com.sydney.utils.CommandUtil;
import com.sydney.utils.FileUtil;
import com.sydney.utils.HttpClientUtil;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
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

    @Resource
    private TranscribeService transcribeService;

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

    @Test
    void uploadAudio() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", cn.hutool.core.io.FileUtil.file("D:\\student\\Aus\\comp5703\\abui\\abui-recordings\\untranscribed\\audio.wav"));

        String result= HttpUtil.post(url + "/api/transcription/new", paramMap);
        System.out.println(result);
    }

    @Test
    void beginTrans() {
        String result1= HttpUtil.get(url+"/api/transcription/transcribe");
        System.out.println(result1);
    }

    @Test
    void getResult() {
        String s = HttpUtil.get(url + "/api/transcription/text");
        System.out.println(s);
    }



    @Test
    void testPost() {

        HashMap<String, Object> paramMap1 = new HashMap<>();
        paramMap1.put("engine_name", "kaldi");
        String s1 = HttpClientUtil.postWithJsonParam(url + "/api/config/engine/load", paramMap1);
        System.out.println(s1);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "m");
        String jsonString = JSONObject.toJSONString(paramMap);
        //链式构建请求
        String result2 = HttpRequest.post(url + "/api/model/load")
                .header(Header.CONTENT_TYPE, "application/json")//头信息，多个头信息多次调用此方法即可
                .body(jsonString)//表单内容
                .execute().body();
        System.out.println(result2);



        HashMap<String, Object> paramMap2 = new HashMap<>();
        paramMap2.put("name", "ds");
        String s2 = HttpClientUtil.postWithJsonParam(url + "/api/dataset/load", paramMap2);
        System.out.println(s2);

        HashMap<String, Object> paramMap3 = new HashMap<>();
        paramMap3.put("name", "pd");
        String s3 = HttpClientUtil.postWithJsonParam(url + "/api/pron-dict/load", paramMap3);
        System.out.println(s3);


    }

    @Test
    void testSendCommandToLinuxServer() {
        // a commands need to be executed
        String cmd = "ls /";
        String result = CommandUtil.getConnect("192.168.200.100", "root", "itcast", cmd);
        System.out.println(result);
    }
}
