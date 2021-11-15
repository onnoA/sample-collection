package com.onnoa.shop.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


/**
 * 二进制工具
 */
public class ByteUtils {

    /**
     * 所有进制字符串
     */
    private static final char[] RadixElements = "abcdefghijkmnpqrstuvwxyz123456789ABCDEFGHIJKLMNPQRSTUVWXYZ"
            .toCharArray();
    /**
     * 16进制数组
     */
    private static final char HexDigits[] = "0123456789abcdef".toCharArray();

    private static final Map<Character, Integer> valueMap = new HashMap<Character, Integer>();

    static {
        for (int i = 0; i < RadixElements.length; i++) {
            valueMap.put(RadixElements[i], i);
        }
    }

    /**
     * 将整数用字符表示
     *
     * @param id
     * @return
     */
    public static String idToCode(BigInteger id) {
        if (id.compareTo(new BigInteger("0")) < 0) {
            return "";
        }
        int radix = RadixElements.length;

        StringBuffer result = new StringBuffer();

        // 将数值转化为字符值
        do {
            BigInteger biRadix = new BigInteger(String.valueOf(radix));
            result.append(RadixElements[(int) (id.mod(biRadix).longValue())]);
            id = id.divide(biRadix);
        } while (id.compareTo(new BigInteger("0")) > 0);
        result.reverse();
        return result.toString();
    }

    public static String idToCode(long id) {
        if (id < 0) {
            return idToCode(id >>> 32) + idToCode(id & 0x00000000FFFFFFFFL);
        }
        int radix = RadixElements.length;
        StringBuffer result = new StringBuffer();
        // 将数值转化为字符值
        do {
            result.append(RadixElements[(int) (id % radix)]);
            id /= radix;
        } while (id > 0);
        result.reverse();
        return result.toString();
    }

    /**
     * Convert byte[] to hex
     * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src) {
        if (src == null) {
            return null;
        }
        if (src.length == 0) {
            return "";
        }
        int byteLen = src.length;
        char strChars[] = new char[byteLen * 2];
        for (int i = 0; i < byteLen; i++) {
            byte b = src[i];
            strChars[i * 2] = HexDigits[b >>> 4 & 0xF];
            strChars[i * 2 + 1] = HexDigits[b & 0xf];
        }
        return new String(strChars);
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static byte[] streamToBytes(InputStream inStream) {
        try {
            int readSize = 1024;
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[readSize]; // buff用于存放循环读取的临时数据
            int rc = 0;
            while ((rc = inStream.read(buff, 0, readSize)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            return swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public static byte[] longToBytes(long... arg) {
        if (arg == null) {
            return null;
        }
        byte[] result = new byte[arg.length * 8];
        for (int i = 0; i < arg.length; i++) {
            result[i * 8 + 0] = (byte) (arg[i] >>> 56);
            result[i * 8 + 1] = (byte) (arg[i] >>> 48);
            result[i * 8 + 2] = (byte) (arg[i] >>> 40);
            result[i * 8 + 3] = (byte) (arg[i] >>> 32);
            result[i * 8 + 4] = (byte) (arg[i] >>> 24);
            result[i * 8 + 5] = (byte) (arg[i] >>> 16);
            result[i * 8 + 6] = (byte) (arg[i] >>> 8);
            result[i * 8 + 7] = (byte) (arg[i]);
        }
        return result;
    }


    public static byte[] getBytesFromFile(InputStream is) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024*1024]; //buff用于存放循环读取的临时数据
        int rc = 0;
        while ((rc = is.read(buff, 0, 1024)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        buff = swapStream.toByteArray(); //in_b为转换之后的结果
        is.close();
        swapStream.close();
        return buff;
    }
}
