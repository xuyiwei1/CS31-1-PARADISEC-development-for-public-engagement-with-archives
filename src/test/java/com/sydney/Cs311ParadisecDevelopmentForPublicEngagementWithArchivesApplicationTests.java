package com.sydney;

import com.alibaba.fastjson.JSON;
import com.sydney.utils.FileUtil;
import com.sydney.utils.HttpClientUtil;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;

@SpringBootTest
class Cs311ParadisecDevelopmentForPublicEngagementWithArchivesApplicationTests {

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
}
