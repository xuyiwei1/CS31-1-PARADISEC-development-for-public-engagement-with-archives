package com.sydney.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class HttpClientUtil {

    //get request no parameters
    public static String getRequestNoParam(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, "utf8");
                System.out.println("content length: " + content.length());
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "No Response";
    }

    public static String getWithParams(String url, Map<String,String> params) throws URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        URIBuilder uriBuilder = new URIBuilder(url);
        params.entrySet().stream().forEach(entry->{
            uriBuilder.setParameter(entry.getKey(),entry.getValue());
        });

        HttpGet httpGet = new HttpGet(uriBuilder.build());

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, "utf8");
                System.out.println(content.length());
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "Fail";
    }

    public static String postWithRequestBody(String url, String requestBody) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String sendUrl = url;

        HttpPost httpPost = new HttpPost(sendUrl);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        CloseableHttpResponse response = null;
        try {
            // 2.设置request-body
            if (!StringUtils.isEmpty(requestBody)) {
                ByteArrayEntity entity = new ByteArrayEntity(requestBody.getBytes(StandardCharsets.UTF_8));
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode() && null != httpEntity) {
                return EntityUtils.toString(httpEntity);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                close(httpClient, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "Fail";
    }

    public static byte[] post(String url, byte[] bytes, Map<String,String> headerMap) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new ByteArrayEntity(bytes));
        HttpResponse httpResponse = null;
        byte[] resBytes = null;
        // 是否需要设置Header
        if (headerMap != null && !headerMap.isEmpty()) {
            Set<String> keySet = headerMap.keySet();
            for (String key : keySet) {
                httpPost.addHeader(key, headerMap.get(key));
            }
        }

        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            System.out.println(EntityUtils.toString(httpEntity));
            int contentLength = (int)httpEntity.getContentLength();
            // 由于返回的结果可能一次性读不完，所以用buff保存每次读取的数据,而当某次读取到的长度返回值为-1时表示读取结束
            byte[] buff = new byte[contentLength];
            int total = 0;
            int len;
            while ((len = httpEntity.getContent().read(buff)) != -1) {
                for(int i = 0; i < len; i++) {
                    resBytes[total+i] = buff[i];
                }
                total = total + len;
            }
            if(total != contentLength) {
                System.out.println("Read http post response buffer error, " + url);
            }

        } catch (Exception e) {
            System.out.println("Something went wrong when call ADXUrl" + e);
        }finally {
            if(httpPost != null) {
                httpPost.releaseConnection();
            }
        }

        return resBytes;
    }

    public static String fileUpload(String url, byte[] file) {
        //Map<String, String> param = new HashMap<>();//其他参数,自行添加

        HttpPost httppost = new HttpPost(url); //fileUploadUrl 上传地址

        //httppost.setHeader("**", **);//根据需要设置头信息

        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        String sResponse = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            // HttpMultipartMode.RFC6532参数的设定是为避免文件名为中文时乱码
            MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.RFC6532);

            byte[] bytes =  file;
            // 添加文件,也可以添加字节流  file的参数名称
            builder.addBinaryBody("file", bytes, ContentType.APPLICATION_JSON, "");
            // 添加参数
//            if (param != null) {
//                for (String key : param.keySet()) {
//                    String value = param.get(key);
//                    if (StringUtils.isNotBlank(value)) {
//                        builder.addTextBody(key, value);
//                    }
//                }
//            }
            HttpEntity reqEntity = builder.build();
            httppost.setEntity(reqEntity);

            response = httpClient.execute(httppost);
            HttpEntity responseEntity = response.getEntity();
            sResponse = EntityUtils.toString(responseEntity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("调用上传文件出错：" + e.getMessage());
        }
        //JSONObject jsonObject = JSONObject.parseObject(sResponse);
//        if (jsonObject == null) {
//            throw new BusinessException("返回值为空！");
//        }
//        return jsonObject.toJavaObject(ResultClass.class);//返回json转对象
        return sResponse;
    }

    public static void close(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) throws IOException{
        if (null != httpClient) {
            httpClient.close();
        }
        if (null != httpResponse) {
            httpResponse.close();
        }
    }

}

