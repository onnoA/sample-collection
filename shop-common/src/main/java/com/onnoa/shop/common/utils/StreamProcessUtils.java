package com.onnoa.shop.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamProcessUtils {

    /**
     * 输入流转换成一个byte数组
     *
     * @param is
     * @return
     * @throws IOException
     */
    public byte[] getBytesFromFile(InputStream is) throws IOException {
        try (ByteArrayOutputStream swapStream = new ByteArrayOutputStream()) {
            byte[] buff = new byte[1024 * 1024];
            int rc = 0;
            while ((rc = is.read(buff, 0, 1024)) > 0) {
                swapStream.write(buff, 0, rc);
                buff = swapStream.toByteArray(); //in_b为转换之后的结果
                is.close();
                return buff;
            }
        }
        return null;
    }
}
