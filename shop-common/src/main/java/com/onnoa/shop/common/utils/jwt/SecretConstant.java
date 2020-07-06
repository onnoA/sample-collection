package com.onnoa.shop.common.utils.jwt;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2019/11/29 16:09
 */
public class SecretConstant {

    //签名秘钥 自定义
    public static final String BASE64SECRET = "***********";

    //超时毫秒数（默认30分钟）
    public static final int EXPIRESSECOND = 1800000;

    //用于JWT加密的密匙 自定义
    public static final String DATAKEY = "************";

}
