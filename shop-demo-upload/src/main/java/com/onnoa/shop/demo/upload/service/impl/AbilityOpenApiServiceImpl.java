package com.onnoa.shop.demo.upload.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.onnoa.shop.demo.upload.service.AbilityOpenApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("abilityOpenApiService")
@Slf4j
public class AbilityOpenApiServiceImpl implements AbilityOpenApiService {


    /**
     * @param params 中需要包含 method("qry.custserv.QryQRcodeByUid"),content("{\"staff_id\":'"+staff_id+"'}")
     * @param params staff_id 为员工id，method 为调用的能开的对应方法名，content 是入参
     * @return
     */
    @Override
    public Map<String, Object> commonRequest(Map<String, Object> params, String url) {
        Map<String, Object> map = new HashMap<String, Object>();
        //生产
//        String url = "http://134.176.102.33:8081/api/rest";
        //测试
//        String url = "http://134.176.102.33:9080/api/rest";
//        String url= ApiServiceImpl.url;
        String method = (String) params.get("method");
        String accessToken = MapUtils.getString(params, "access_token");
        Object content = params.get("content");
        BasicHttpParams bp = new BasicHttpParams();
        HttpClient httpclient = new DefaultHttpClient(bp);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
        httpPost.setHeader("", "");//设置http头信息
        httpPost.setHeader("", "");//设置http头信息
        //ES标签查询时需要带如下参数accountCode、appSecret、ssoType
        String accountCode = (String) params.get("accountCode");
        if (StringUtils.isNotEmpty(accountCode)) {
            String appSecret = (String) params.get("appSecret");
            httpPost.setHeader("accountCode", accountCode);//设置http头信息
            httpPost.setHeader("appSecret", appSecret);//设置http头信息
            httpPost.setHeader("ssoType", "APP_TOKEN");//设置http头信息
        }
        try {
            JSONObject postData = new JSONObject();

            postData.put("method", method);
            postData.put("access_token", accessToken);
            Object version = params.get("version");
            if (version != null) {
                postData.put("version", params.get("version"));
            } else {
                postData.put("version", "1.0");
            }
            if (!StringUtils.equals("order.sms.sendsms", method)) {
                postData.put("status", "1");
            }
            postData.put("content", content);
            //生产测试用
            postData.put("status", MapUtils.getString(params, "status"));

            httpPost.setEntity(new StringEntity(postData.toString(), HTTP.UTF_8));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("OK");
                String st = EntityUtils.toString(httpResponse.getEntity());
                st = st.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
                String returnStr = java.net.URLDecoder.decode(st);
                System.out.println("返回的参数:" + returnStr);
                Map resO = JSONObject.parseObject(returnStr, Map.class);
                return resO;
            } else {
                throw new RuntimeException("接口调用失败" + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("接口其它异常" + e.getMessage(), e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    public static void main(String[] args) {
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("reqSystem", "YWWB");
        contentMap.put("reqPwd", "HNYWWB");
        contentMap.put("contractCode", "HNCSA2006921CGN00");
        //JSONObject postData = new JSONObject();
        Map<String, Object> postData = Maps.newHashMap();
        postData.put("method", "qry.contract.QryContractFile");
        postData.put("access_token", "OTBkMjJiM2Y0NmVjNzdmOTc0NWFkZWMyZGU1MThhMmI="/*MapUtils.getString(params, "access_token")*/);
        postData.put("content", contentMap);
        postData.put("status", "0");
        String s = JSONObject.toJSONString(postData);
        System.out.println(s);

    }

    /**
     * @param params
     * @param url    测试 String url = "http://134.176.102.33:9080/api/rest"  生产 String url = "http://134.176.102.33:8081/api/rest";
     * @return
     */
    @Override
    public Map<String, Object> postAbility(Map<String, Object> params, String url) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString(params), HTTP.UTF_8));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String st = EntityUtils.toString(httpResponse.getEntity());
                return JSONObject.parseObject(st, Map.class);
            } else {
                throw new RuntimeException(url + "接口调用失败" + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            log.error("请求url为：{} 的接口请求异常:  ", url, e);
            throw new RuntimeException("接口其它异常" + e.getMessage(), e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }
}

