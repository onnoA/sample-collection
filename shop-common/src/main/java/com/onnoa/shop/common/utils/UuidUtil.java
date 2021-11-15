package com.onnoa.shop.common.utils;


import java.util.UUID;

/**
 * UUID工具
 */
public abstract class UuidUtil {
    public static String genUuid() {
        return getUuid().toString();
    }

    /**
     * 产生字符串的uuid，并且没有连接线
     *
     * @return
     */
    public static String genUuidNoLine() {
        UUID uuid = getUuid();
        Long most = uuid.getMostSignificantBits();
        Long least = uuid.getLeastSignificantBits();
        return ByteUtils.bytesToHexString(ByteUtils.longToBytes(most, least));
    }

    /**
     * 产生短的UUID
     *
     * @return
     */
    public static String shortUuid() {
        UUID uuid = getUuid();
        Long most = uuid.getMostSignificantBits();
        Long least = uuid.getLeastSignificantBits();
        return ByteUtils.idToCode(most) + ByteUtils.idToCode(least);
    }

    //这个方法还需要改进随机数的生成
    private static UUID getUuid() {
        return UUID.randomUUID();
    }
}
