package com.onnoa.shop.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.onnoa.shop.common.spring.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 用于发送http请求并返回结果的客户端
 *
 * @author shuofeng
 */
@Component
public class HttpClient {
    private static Logger logger = Logger.getLogger(HttpClient.class);
    private static Gson gson = new GsonBuilder().create();

    private static HttpConfig httpConfig = null;

    private static final String USER_AGENT = "Mozilla/5.0";

    @PostConstruct
    public void init() {
        httpConfig = SpringContextHolder.getBean("httpConfig", HttpConfig.class);
    }

    public static <T> T post(String url, String body, Class<T> clazz) {
        String json = post(url, body, (Map<String, String>) null, false);
        if (StringUtils.isBlank(json)) {
            json = "{}";
        }
        return gson.fromJson(json, clazz);
    }

    public static String post(String url, String body, int timeOut) {
        return post(url, body, timeOut, (Map<String, String>) null, false);
    }

    public static String post(String url, String params, int timeOut, Map<String, String> headers, boolean returnErr) {
        CloseableHttpClient client = null;
        RuntimeException throwResult = null;

        HttpEntityByPost httpEntityByPost = new HttpEntityByPost(url, params, timeOut, headers, client).invoke();
        String result = httpEntityByPost.getResult() == null ? "" : httpEntityByPost.getResult();

        if (throwResult != null) {
            throw throwResult;
        }
        return result;
    }

    public static <T> T post(String url, String body, Class<T> clazz, boolean returnErr) {
        String json = post(url, body, (Map<String, String>) null, returnErr);
        if (StringUtils.isBlank(json)) {
            json = "{}";
        }
        return gson.fromJson(json, clazz);
    }

    public static <T> T post(String url, Map<String, String> body, Class<T> clazz) {
        String json = post(url, body, (Map<String, String>) null, false);
        if (StringUtils.isBlank(json)) {
            json = "{}";
        }
        return gson.fromJson(json, clazz);
    }

    public static <T> T post(String url, Map<String, String> body, Class<T> clazz, boolean returnErr) {
        String json = post(url, body, (Map<String, String>) null, returnErr);
        if (StringUtils.isBlank(json)) {
            json = "{}";
        }
        return gson.fromJson(json, clazz);
    }

    public static String post(String url, String body) {
        return post(url, body, (Map<String, String>) null, false);
    }

    public static String post(String url, String body, boolean returnErr) {
        return post(url, body, (Map<String, String>) null, returnErr);
    }

    public static String post(String url, Map<String, String> body, boolean returnErr) {
        return post(url, body, (Map<String, String>) null, returnErr);
    }

    public static String post(String url, Map<String, String> body) {
        return post(url, body, (Map<String, String>) null, false);
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers, boolean returnErr) {
        CloseableHttpClient client = null;
        String errorStr = null;

        try {
            //https 的用例
            if (url.startsWith("https")) {
                client = getHttpClientForHttps();
            } else {
                client = HttpClients.createDefault();
            }
            return getHttpEntityByPost(client, url, params, headers);
        } catch (KeyStoreException e) {
            errorStr = e.getMessage();
            logger.error(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            errorStr = e.getMessage();
            logger.error(e.getMessage());
        } catch (KeyManagementException e) {
            errorStr = e.getMessage();
            logger.error(e.getMessage());
        } catch (IOException e) {
            errorStr = e.getMessage();
            logger.error(e.getMessage());
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        if (returnErr) {
            return errorStr;
        } else {
            return null;
        }
    }


    public static String post(String url, String params, Map<String, String> headers, boolean returnErr) {
        CloseableHttpClient client = null;
        RuntimeException throwResult = null;
        String errorStr = null;

        try {
            //https 的用例
            if (url.startsWith("https")) {
                client = getHttpClientForHttps();
            } else {
                client = HttpClients.createDefault();
            }
            return getHttpEntityByPost(client, url, params, headers);
        } catch (KeyStoreException e) {
            errorStr = e.getMessage();
            logger.error(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            errorStr = e.getMessage();
            logger.error(e.getMessage());
        } catch (KeyManagementException e) {
            errorStr = e.getMessage();
            logger.error(e.getMessage());
        } catch (IOException e) {
            errorStr = e.getMessage();
            logger.error(e.getMessage());
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        if (throwResult != null) {
            throw throwResult;
        }
        if (returnErr) {
            return errorStr;
        } else {
            return null;
        }
    }


    /**
     * post 方式执行 http 请求
     *
     * @param url
     * @param body
     * @param headers
     * @return
     */
    private static String getHttpEntityByPost(CloseableHttpClient client, String
            url, Map<String, String> body, Map<String, String> headers) throws
            IOException {

        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(httpConfig.getConnectTimeout()).build();
        post.setConfig(requestConfig);
        if (headers != null) {
            Set<String> keys = headers.keySet();
            Iterator<String> i = keys.iterator();
            while (i.hasNext()) {
                String key = i.next();
                post.addHeader(key, headers.get(key));
            }
        }
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        body.forEach((key, value) -> {
            params.add(new BasicNameValuePair((String) key, (String) value));
        });
        post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));

        CloseableHttpResponse httpResponse = client.execute(post);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }


    /**
     * post 方式执行 http 请求
     *
     * @param url
     * @param body
     * @param headers
     * @return
     */
    private static String getHttpEntityByPost(CloseableHttpClient client, String
            url, String body, Map<String, String> headers) throws
            IOException {
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(httpConfig.getConnectTimeout()).build();
        post.setConfig(requestConfig);
        if (headers != null) {
            Set<String> keys = headers.keySet();
            Iterator<String> i = keys.iterator();
            while (i.hasNext()) {
                String key = i.next();
                post.addHeader(key, headers.get(key));
            }
        }
        if (body.startsWith("&")) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String[] formData = body.split("&");
            for (int i = 0; i < formData.length; i++) {
                String[] keyValue = formData[i].split("#");
                params.add(new BasicNameValuePair(keyValue[0], keyValue[1]));
            }
            post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        } else if (body.startsWith("{") && isJson(body)) {
            post.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
            post.addHeader(USER_AGENT, USER_AGENT);
            StringEntity entity = new StringEntity(body, HTTP.UTF_8);
            post.setEntity(entity);
        } else {
            StringEntity stringEntity = new StringEntity(body, "utf-8");
//            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, contentType));
            post.setEntity(stringEntity);
        }

        CloseableHttpResponse httpResponse = client.execute(post);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }

    /**
     * https 专用的 httpClient 针对 https ，客户端httpClient信任所有的证书
     *
     * @return
     */
    private static CloseableHttpClient getHttpClientForHttps() throws
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            //信任所有
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        HttpClients.custom().setSSLSocketFactory(sslsf).build();
        return HttpClients.createDefault();
    }


//
//    public static String get(String url, Map<String, String> headers) {
//
//        String httpEntity = "";
//        try {
//            httpEntity = getHttpEntityByGet(url, headers);
//
//            if (httpEntity != null) {
//                LogUtils.info(LOGGER, "Response content: " + httpEntity);
//                return httpEntity;
//            }
//        } catch (Exception e) {
//            LOGGER.error("EntityUtils ERROR");
//            LOGGER.error(e.getCause());
//            httpEntity = JSON.toJSONString(e);
//
//        }
//
//        return httpEntity;
//    }
//
//    /**
//     * get 请求
//     *
//     * @param url
//     * @param headers
//     * @return
//     */
//    public static String getHttpEntityByGet(String url, Map<String, String> headers) {
//        CloseableHttpClient client = null;
//        //https 的用例
//        if (url.startsWith("https")) {
//            client = getHttpClientForHttpsCase();
//
//        } else {
//            client = HttpClients.createDefault();
//
//        }
//
//        try {
//            HttpGet httpGet = new HttpGet(url);
//
//            if (headers != null) {
//                Set<String> keys = headers.keySet();
//                for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
//                    String key = i.next();
//                    httpGet.addHeader(key, headers.get(key));
//                }
//            }
//
//            LogUtils.info(LOGGER, "Send Request type: GET" + "\t\nSend Request url: " + url
//                    + "\t\nSend Request header: " + headers);
//
//            HttpResponse httpResponse = client.execute(httpGet);
//
//            return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
//
//        } catch (Exception e) {
//            LOGGER.error("Send Request ERROR" + e.getMessage());
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                client.close();
//            } catch (IOException e) {
//                LogUtils.error(LOGGER, e.getMessage(), e);
//            }
//        }
//
//    }
//

//
//    public static String get(String url) {
//        return get(url, null);
//    }
//

    /**
     * 校验字符串是否是 json
     *
     * @param json
     * @return
     */
    public static boolean isJson(String json) {
        boolean result = true;

        if (StringUtils.isBlank(json)) {
            result = false;
        }
        new JsonParser().parse(json);

        return result;
    }

    /**
     * post 方式执行 http 请求
     *
     * @param url
     * @param body
     * @param headers
     * @return
     */
    public static String getHttpEntityByPost(CloseableHttpClient client, String url, String body, int timeOut,
                                             Map<String, String> headers) throws IOException {
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeOut).setConnectionRequestTimeout(timeOut)
                .setSocketTimeout(timeOut).build();
        post.setConfig(requestConfig);
        if (headers != null) {
            /*
             * Set<String> keys = headers.keySet(); Iterator<String> i = keys.iterator(); while (i.hasNext()) { String
             * key = i.next(); post.addHeader(key, headers.get(key)); }
             */
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (body.startsWith("&")) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String[] formData = body.split("&");
            for (int i = 0; i < formData.length; i++) {
                String[] keyValue = formData[i].split("#");
                params.add(new BasicNameValuePair(keyValue[0], keyValue[1]));
            }
            post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        } else if (body.startsWith("{") && isJson(body)) {
            post.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
            post.addHeader(USER_AGENT, USER_AGENT);
            StringEntity entity = new StringEntity(body, HTTP.UTF_8);
            post.setEntity(entity);
        } else {
            StringEntity stringEntity = new StringEntity(body, "utf-8");
            // stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, contentType));
            post.setEntity(stringEntity);
        }

        CloseableHttpResponse httpResponse = client.execute(post);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }
}
