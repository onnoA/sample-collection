package com.onnoa.shop.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 基于 httpclient 4.3.1版本的 http工具类
 * @Author: onnoA
 * @Date: 2020/4/21 10:04
 */
@Slf4j
public class HttpUtils {

    public static class HttpResult {
        public int stateCode;
        public String content;
    }

    public static final String DefaultCharset = "UTF-8";

    public static String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Win32)";

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, DefaultCharset);
    }

    public static <T extends Object> String doPost(String url, Map<String, String> params) {
        return doPost(url, params, DefaultCharset);
    }

    private static CloseableHttpClient getHttpClient() {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(600000).setSocketTimeout(600000).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    /**
     * HTTP Get 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }

        log.debug("http:get:request:url: {}, params:{}", url, params);
        CloseableHttpResponse response = null;
        String result = null;

        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    Object value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value.toString()));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            response = getHttpClient().execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
            response = null;
        }
        log.debug("http:get:result: {}", result);
        return result;
    }

    /**
     * HTTP Post 获取内容 请求响应，非200将抛出异常。
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doPost(String url, Map<String, String> params, String charset) {

        HttpResult result = doPostReturnStateCode(url, params, charset);
        if (result.stateCode != 200) {
            throw new RuntimeException("HttpClient,error status code :" + result.stateCode);
        }
        return result.content;

    }

    public static HttpResult doGetReturnStateCode(String url, Map<String, String> params) {
        return doGetReturnStateCode(url, params, DefaultCharset);
    }

    public static HttpResult doPostReturnStateCode(String url, Map<String, String> params) {
        return doPostReturnStateCode(url, params, DefaultCharset);
    }

    /**
     * HTTP Get 获取内容，返回状态和请求内容 <br>
     * 返回状态码，不是200也成功返回结果
     *
     * @param url     请求的url地址
     * @param params  请求参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static HttpResult doGetReturnStateCode(String url, Map<String, String> params,
                                                  String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        CloseableHttpClient httpClient = getHttpClient();
        HttpResult httpResult = new HttpResult();
        int statusCode = -1;
        log.debug("http:get:request:url: {}, params:{}", url, params);
        CloseableHttpResponse response = null;
        String result = null;


        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    Object value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value.toString()));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);

            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                }
            }
            httpClient = null;
            response = null;
        }
        log.debug("http:get:result: {}", result);
        httpResult.content = result;
        httpResult.stateCode = statusCode;

        return httpResult;
    }

    /**
     * HTTP Post 获取内容,返回状态和请求内容。<br>
     * 返回状态码，不是200也成功返回结果
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static <T extends Object> HttpResult doPostReturnStateCode(String url, Map<String, T> params,
                                                                      String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        CloseableHttpClient httpClient = getHttpClient();
        HttpResult result = new HttpResult();

        log.debug("http:post:request:url: {}, params:{}", url, params.toString());
        CloseableHttpResponse response = null;

        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, T> entry : params.entrySet()) {
                    Object value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value.toString()));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
            }
            response = httpClient.execute(httpPost);
            result.stateCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result.content = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                }
            }
            httpClient = null;
            response = null;
        }
        log.debug("http:post:result:{}, {}", result.stateCode, result.content);

        return result;
    }

    /**
     * 短信专用请求
     */
    public static String httpAction(String url, String params, String methodType, String encoding, String contentType) {
        if (StringUtils.isBlank(encoding)) {
            encoding = "UTF-8";
        }
        if (StringUtils.isBlank(methodType)) {
            methodType = "GET";
        }
        if (StringUtils.isBlank(contentType)) {
            contentType = "application/x-www-form-urlencoded";
        }
        URL u = null;
        HttpURLConnection con = null;
        StringBuffer content = new StringBuffer();
        // 发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod(methodType.toUpperCase());
            con.setRequestProperty("Content-Type", contentType);
            con.setRequestProperty("connection", "Keep-Alive");
            // con.setRequestProperty("Content-Type","application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(2000);
            // 构建请求参数
            if (methodType.equalsIgnoreCase("post")) {
                OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), encoding);
                osw.write(params);
                osw.flush();
                osw.close();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), encoding));
            String line = "";
            while ((line = br.readLine()) != null) {
                content.append(line);
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return content.toString();
    }

    /**
     * 访问https的网站
     *
     * @param httpclient
     */
    public static void enableSSL(DefaultHttpClient httpclient) {
        //调用ssl
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{truseAllManager}, null);
            SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme https = new Scheme("https", sf, 443);
            httpclient.getConnectionManager().getSchemeRegistry().register(https);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重写验证方法，取消检测ssl
     */
    private static TrustManager truseAllManager = new X509TrustManager() {

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub
        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    };

    /**
     * 获取DefaultHttpClient对象
     *
     * @param charset 字符编码
     * @return DefaultHttpClient对象
     */
    public static DefaultHttpClient getDefaultHttpClient(final String charset) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        // 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
        httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);
        httpclient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
        httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, charset == null ? DefaultCharset : charset);
        // 浏览器兼容性
        httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        // 定义重试策略
        httpclient.setHttpRequestRetryHandler(requestRetryHandler);
        return httpclient;
    }

    /**
     * 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
     */
    private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
        // 自定义的恢复策略
        public boolean retryRequest(IOException exception, int executionCount,
                                    HttpContext context) {
            // 设置恢复策略，在发生异常时候将自动重试3次
            if (executionCount >= 3) {
                // 如果连接次数超过了最大值则停止重试
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                // 如果服务器连接失败重试
                return true;
            }
            if (exception instanceof SSLHandshakeException) {
                // 不要重试ssl连接异常
                return false;
            }
            HttpRequest request = (HttpRequest) context
                    .getAttribute(ExecutionContext.HTTP_REQUEST);
            boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
            if (!idempotent) {
                // 重试，如果请求是考虑幂等
                return true;
            }
            return false;
        }
    };


}
