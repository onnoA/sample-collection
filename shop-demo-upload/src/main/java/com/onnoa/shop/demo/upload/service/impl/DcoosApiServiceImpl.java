package com.onnoa.shop.demo.upload.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.onnoa.shop.demo.upload.service.DcoosApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
@Slf4j
public class DcoosApiServiceImpl implements DcoosApiService {
    @Override
    public Map<String, Object> post(Map<String, Object> params, Map<String, String> configParams) {
        Map<String, Object> map = new HashMap<String, Object>();
        String method = (String) params.get("method");
//        String url = basicUrl+"/"+method;
        String url = MapUtils.getString(configParams, "basicUrl") + "/" + method;
        BasicHttpParams bp = new BasicHttpParams();
        HttpClient httpclient = new DefaultHttpClient(bp);
        HttpPost httpPost = new HttpPost(url);
        //设置http头信息
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
//        httpPost.setHeader("X-CTG-Request-ID", UUID.randomUUID().toString());
//        httpPost.setHeader("X-APP-ID", XAPPID);
//        httpPost.setHeader("X-APP-KEY", XAPPKEY);
        httpPost.setHeader("X-APP-ID", MapUtils.getString(configParams, "XAPPID"));
        httpPost.setHeader("X-APP-KEY", MapUtils.getString(configParams, "XAPPKEY"));
        //System.out.println(genExchangeId(MapUtils.getString(configParams, "clientId")));
        httpPost.setHeader("X-CTG-Request-ID", genExchangeId(MapUtils.getString(configParams, "clientId")));
        Map<String, Object> lineMap = (Map<String, Object>) params.get("line");
        if (lineMap != null && lineMap.size() > 0) {
            httpPost.setHeader("X-CTG-Lan-ID", (String) lineMap.get("comId"));
            httpPost.setHeader("lanId", (String) lineMap.get("lanId"));
        }
        Map<String, Object> userInfo = (Map<String, Object>) params.get("userInfo");
        if (userInfo != null && userInfo.size() > 0) {
            httpPost.setHeader("staffNo", (String) userInfo.get("staffId"));
            httpPost.setHeader("staffCode", (String) userInfo.get("user_code"));
        } else {
            httpPost.setHeader("staffNo", "4341");
            httpPost.setHeader("staffCode", "ywjsjwq");
        }
        System.out.println(httpPost.getAllHeaders());
        Map<String, Object> mapParam = (Map) params.get("params");
        try {
            JSONObject patchData = new JSONObject();
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Set<Map.Entry<String, Object>> set = mapParam.entrySet();
            for (Map.Entry<String, Object> entry : set) {
//                list.add(new BasicNameValuePair(entry.getKey(), (String)entry.getValue()));
                patchData.put(entry.getKey(), entry.getValue());
            }
            System.out.println(patchData);
            httpPost.setEntity(new StringEntity(patchData.toString(), HTTP.UTF_8));
//            httpPatch.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity e = httpResponse.getEntity();
                String st = EntityUtils.toString(httpResponse.getEntity());
                StringBuffer buf = new StringBuffer(st);
                String returnStr = java.net.URLDecoder.decode(replacer(buf));
                System.out.println("返回的参数:" + returnStr);
                JSONObject res = JSONObject.parseObject(returnStr);
                map.put("result", res);
                map.put("flag", true);
            } else {
                String st = EntityUtils.toString(httpResponse.getEntity());
                StringBuffer buf = new StringBuffer(st);
                String returnStr = java.net.URLDecoder.decode(replacer(buf));
                System.out.println("返回的参数:" + returnStr);
                JSONObject res = JSONObject.parseObject(returnStr);
                map.put("flag", false);
                map.put("result", res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("接口其它异常");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return map;
    }

    @Override
    public Map<String, Object> post(Map<String, Object> params, Map<String, String> header, String url) {
        Map<String, Object> map = Maps.newHashMap();
        HttpPost httpPost = new HttpPost(url);
        BasicHttpParams bp = new BasicHttpParams();
        HttpClient httpclient = new DefaultHttpClient(bp);
        // 设置请求头信息
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setHeader("X-APP-ID", MapUtils.getString(header, "XAPPID"));
        httpPost.setHeader("X-APP-KEY", MapUtils.getString(header, "XAPPKEY"));
        httpPost.setHeader("X-CTG-Request-ID", MapUtils.getString(header, "XCTGRequestID"));
        Map<String, Object> lineMap = (Map<String, Object>) params.get("line");
        if (lineMap != null && lineMap.size() > 0) {
            httpPost.setHeader("X-CTG-Lan-ID", (String) lineMap.get("comId"));
            httpPost.setHeader("lanId", (String) lineMap.get("lanId"));
        }
        Map<String, Object> userInfo = (Map<String, Object>) params.get("userInfo");
        if (userInfo != null && userInfo.size() > 0) {
            httpPost.setHeader("staffNo", (String) userInfo.get("staffId"));
            httpPost.setHeader("staffCode", (String) userInfo.get("user_code"));
        } else {
            httpPost.setHeader("staffNo", "4341");
            httpPost.setHeader("staffCode", "ywjsjwq");
        }
        Map<String, Object> mapParam = (Map) params.get("params");
        try {
            JSONObject patchData = new JSONObject();
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Set<Map.Entry<String, Object>> set = mapParam.entrySet();
            for (Map.Entry<String, Object> entry : set) {
                patchData.put(entry.getKey(), entry.getValue());
            }
            System.out.println(patchData);
            httpPost.setEntity(new StringEntity(patchData.toString(), HTTP.UTF_8));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity e = httpResponse.getEntity();
                String st = EntityUtils.toString(httpResponse.getEntity());
                StringBuffer buf = new StringBuffer(st);
                String returnStr = java.net.URLDecoder.decode(replacer(buf));
                System.out.println("返回的参数:" + returnStr);
                JSONObject res = JSONObject.parseObject(returnStr);
                map.put("result", res);
                map.put("flag", true);
            } else {
                String st = EntityUtils.toString(httpResponse.getEntity());
                StringBuffer buf = new StringBuffer(st);
                String returnStr = java.net.URLDecoder.decode(replacer(buf));
                System.out.println("返回的参数:" + returnStr);
                JSONObject res = JSONObject.parseObject(returnStr);
                map.put("flag", false);
                map.put("result", res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            StringBuffer sb = new StringBuffer();
            sb.append("请求接口:{}").append(url).append("异常");
            log.info(sb.toString());
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return map;
    }

    public static String genExchangeId(String client_id) {
        if (StringUtils.isBlank(client_id)) {
            client_id = "";
        } else {
            client_id = client_id.trim();
        }
        String date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        Random random = new Random();
        int randVal = random.nextInt(10000000);

        return client_id + date + String.format("%07d", randVal);
    }

    public static String replacer(StringBuffer outBuffer) {
        String data = outBuffer.toString();
        try {
            data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            data = data.replaceAll("\\+", "%2B");
            data = URLDecoder.decode(data, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
