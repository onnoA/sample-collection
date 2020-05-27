package com.onnoa.shop.demo.video.cache;


import com.onnoa.shop.common.redis.cache.CacheFacade;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-02 11:27
 */
public class QiniuCache extends CacheFacade {

    public static final QiniuCache QINIU_URL_SESSION = new QiniuCache("shop:qiniu:url:");
    public static final QiniuCache QINIU_IMAGEINFO_SESSION = new QiniuCache("shop:qiniu:imageinfo:");

    protected QiniuCache(String keyPrefix) {
        super(keyPrefix);
    }

}
