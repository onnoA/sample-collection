package com.onnoa.shop.demo.video.cache;


import com.onnoa.shop.common.redis.cache.CacheFacade;

public class QiniuCache extends CacheFacade {

    public static final QiniuCache QINIU_URL_SESSION = new QiniuCache("shop:qiniu:url:");
    public static final QiniuCache QINIU_IMAGEINFO_SESSION = new QiniuCache("shop:qiniu:imageinfo:");

    protected QiniuCache(String keyPrefix) {
        super(keyPrefix);
    }

}
