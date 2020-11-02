package com.onnoa.shop.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * 腾讯云 - 第三方服务商 接口， 对接人脸识别接口
 */
public class UserFaceAuthUtils {
    public static String calcAuthorization(String source, String secretId, String secretKey, String datetime)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
        Mac mac = Mac.getInstance("HmacSHA1");
        Key sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(sKey);
        byte[] hash = mac.doFinal(signStr.getBytes("UTF-8"));
        String sig = new BASE64Encoder().encode(hash);

        String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
        return auth;
    }

    public static String urlencode(Map<?, ?> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
                    URLEncoder.encode(entry.getValue().toString(), "UTF-8")
            ));
        }
        return sb.toString();
    }


    /**
     * 人脸识别
     *
     * @param bs64 人脸照片  base 64
     * @param name 姓名
     * @param cdnb 身份证号码
     * @param soft 是否软控制，如果软控制，  则识别结果没有那么严格
     * @return
     */
    public static boolean face(String bs64, String name, String cdnb, boolean soft) {

        try {
            //云市场分配的密钥Id
            String secretId = "*********";
            //云市场分配的密钥Key
            String secretKey = "********";
            String source = "market";

            Calendar cd = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String datetime = sdf.format(cd.getTime());
            // 签名
            String auth = calcAuthorization(source, secretId, secretKey, datetime);
            // 请求方法
            String method = "POST";
            // 请求头
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("X-Source", source);
            headers.put("X-Date", datetime);
            headers.put("Authorization", auth);

            // 查询参数
            Map<String, String> queryParams = new HashMap<String, String>();

            // body参数
            Map<String, String> bodyParams = new HashMap<String, String>();
            bodyParams.put("base64Str", bs64);
            bodyParams.put("liveChk", "0");
            bodyParams.put("name", name);
            bodyParams.put("number", cdnb);
            // url参数拼接
            String url = "http://service-0ob4jwmn-1300755093.ap-beijing.apigateway.myqcloud.com/release/efficient/idfaceIdentity";
            if (!queryParams.isEmpty()) {
                url += "?" + urlencode(queryParams);
            }

            BufferedReader in = null;
            try {
                URL realUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod(method);

                // request headers
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }

                // request body
                Map<String, Boolean> methods = new HashMap<>();
                methods.put("POST", true);
                methods.put("PUT", true);
                methods.put("PATCH", true);
                Boolean hasBody = methods.get(method);
                if (hasBody != null) {
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    conn.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(bodyParams));
                    out.flush();
                    out.close();
                }

                // 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                String result = "";
                while ((line = in.readLine()) != null) {
                    result += line;
                }

                System.out.println(result);
                Map<String, Object> map = JSON.parseObject(result, Map.class);
                if (0 == Integer.parseInt(map.get("error_code").toString())) {
                    if (map.get("result") != null) {
                        JSONObject r = (JSONObject) map.get("result");
                        int similarity = new BigDecimal(r.get("Similarity").toString()).intValue();
                        int validateResult = Integer.parseInt(r.get("Validate_Result").toString());

                        if (soft) {
                            if ((validateResult == 1 || validateResult == 2) && (similarity >= 40)) {
                                // 识别成功  且相似度大于45分，才认为是匹配的人
                                return true;
                            }
                        } else {
                            if (validateResult == 1 && similarity >= 45) {
                                // 识别成功  且相似度大于45分，才认为是匹配的人
                                return true;
                            }
                        }


                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getImgStr(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理

        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(data);
    }
}
