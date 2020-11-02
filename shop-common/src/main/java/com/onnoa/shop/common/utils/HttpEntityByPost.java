package com.onnoa.shop.common.utils;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpEntityByPost {
    private static final Logger LOGGER = Logger.getLogger(HttpClient.class);

    public String getResult() {
        return result;
    }

    private String result;

    private String url;

    private String params;

    private int timeOut;

    private Map<String, String> headers;

    private CloseableHttpClient client;

    public HttpEntityByPost(String url, String params, int timeOut, Map<String, String> headers, CloseableHttpClient client) {
        this.url = url;
        this.params = params;
        this.timeOut = timeOut;
        this.headers = headers;
        this.client = client;
    }

    /**
     * https 专用的 httpClient 针对 https ，客户端httpClient信任所有的证书
     *
     * @return
     */
    public static CloseableHttpClient getHttpClientForHttps()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 信任所有
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        HttpClients.custom().setSSLSocketFactory(sslsf).build();
        return HttpClients.createDefault();
    }

    public CloseableHttpClient getClient() {
        return client;
    }

    public HttpEntityByPost invoke() {
        try {
            // https 的用例
            if (url.startsWith("https")) {
                client = getHttpClientForHttps();
            }
            else {
                client = HttpClients.createDefault();
            }
            try {
                result = HttpClient.getHttpEntityByPost(client, url, params, timeOut, headers);
            }
            catch (IOException e) {
                LOGGER.info(e.getMessage(), e);
            }
            return this;
        }
        catch (KeyStoreException e) {
            result = e.getMessage();
            LOGGER.error(e.getMessage());
        }
        catch (NoSuchAlgorithmException e) {
            result = e.getMessage();
            LOGGER.error(e.getMessage());
        }
        catch (KeyManagementException e) {
            result = e.getMessage();
            LOGGER.error(e.getMessage());
        }
        finally {
            if (client != null) {
                try {
                    client.close();
                }
                catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return this;
    }

}
