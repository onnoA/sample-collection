package com.onnoa.shop.common.utils;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class GetBase64Utils {

    public static String getBase64(File file) throws IOException {
        InputStream is = null;
        ByteArrayOutputStream outSteam = null;
        byte[] bytes = null;
        try {
            is = new FileInputStream(file);
            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            bytes = outSteam.toByteArray();
        }
        finally {
            IOUtils.closeQuietly(outSteam);
            IOUtils.closeQuietly(is);
        }
        return getBase64(bytes);
    }

    public static String getBase64(byte[] imageByte) {
        // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
        // BASE64Encoder base64Encoder = new BASE64Encoder();
        // return base64Encoder.encode(imageByte);
        return Base64.getEncoder().encodeToString(imageByte);
    }
}
