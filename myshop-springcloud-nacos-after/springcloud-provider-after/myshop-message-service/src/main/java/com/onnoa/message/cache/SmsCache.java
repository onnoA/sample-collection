package com.onnoa.message.cache;

import com.onnoa.shop.common.redis.cache.CacheFacade;

public class SmsCache extends CacheFacade {

    public static CacheFacade USER_SMS_CODE = new SmsCache("myshop:sms:code:");

    protected SmsCache(String keyPrefix) {
        super(keyPrefix);
    }
}
