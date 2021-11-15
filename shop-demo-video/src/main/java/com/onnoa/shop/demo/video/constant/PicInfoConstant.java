package com.onnoa.shop.demo.video.constant;

/**
 * 视图片信息常量
 */
public class PicInfoConstant {
    /**
     * 图片待处理
     */
    public static final Integer AWAIT_STATUS=0;

    /**
     * 图片处理完成
     */
    public static final Integer AVAILABLE_STATUS=1;

    /**
     * 图片处理异常
     */
    public static final Integer PICEXCEPTION_STATUS=2;
    /**
     * 图片处理类型 : 小图
     */
    public static final Integer SMALL_TYPE=1;

    /**
     * 图片处理类型 : 中图
     */
    public static final Integer MEDIUM_TYPE=2;

    /**
     * 图片处理类型 : 大图
     */
    public static final Integer LARGE_TYPE =3;

    /**
     * 图片处理类型 : 原图
     */
    public static final Integer ORIGIN_TYPE =10;
    /**
     * 图片格式 :gif,jpg等通用格式
     */
    public static final Integer GENERAL_FORMAT=1;
    /**
     *  图片格式 :webp 不通用格式
     */
    public static final Integer NOGENERAL_FORMAT=2;

    /**
     * 定时器同时处理个数
     */
    public static final Integer COUNT_TIMINGJOB=10;
    /**
     * 图片格式
     */
    public static final String FILE_PICSUFFIX_JPG= "jpg";
    /**
     * 图片格式
     */
    public static final String FILE_PICSUFFIX_GIF= "gif";
    /**
     * 图片格式
     */
    public static final String FILE_PICSUFFIX_WEBP= "webp";
}
