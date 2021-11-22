package com.onnoa.shop.common.utils;

import java.security.MessageDigest;

/**
 * @className: DigitalMD5
 * @description:
 * @author: onnoA
 * @date: 2021/11/17
 **/
public class DigitalMD5Util {


    public static String byteArrayToString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToNumString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToNumString(byte b) {
        int _b = b;
        if (_b < 0) {
            _b = 256 + _b;
        }
        return String.valueOf(_b);
    }

    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {

        }
        return resultString;
    }
}
