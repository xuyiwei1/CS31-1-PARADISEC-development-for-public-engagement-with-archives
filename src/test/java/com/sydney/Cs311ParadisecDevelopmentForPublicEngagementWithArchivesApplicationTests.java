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

        String result2 = HttpRequest.post(url + "/api/model/load")
                .header(Header.CONTENT_TYPE, "application/json")
                .body(jsonString)
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
    public void test() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", cn.hutool.core.io.FileUtil.file("D:\\ChromeDownload\\model.zip"));

        String result= HttpUtil.post(url+"/api/model/upload", paramMap);
        System.out.println(result);
    }

    @Test
    void testUploadModel() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", cn.hutool.core.io.FileUtil.file("D:\\ChromeDownload\\model.zip"));

        String result= HttpUtil.post(url+"/api/model/upload", paramMap);
        System.out.println(result);
    }

    @Test
    void testSendCommandToLinuxServer() {
        // a commands need to be executed
        String cmd = "ls /";
        String result = CommandUtil.getConnect("192.168.200.100", "root", "itcast", cmd);
        System.out.println(result);
    }
}
