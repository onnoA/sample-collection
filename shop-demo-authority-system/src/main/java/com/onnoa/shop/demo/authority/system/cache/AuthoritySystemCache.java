package com.onnoa.shop.demo.authority.system.cache;

import com.onnoa.shop.common.redis.cache.CacheFacade;

public class AuthoritySystemCache extends CacheFacade {

    public static CacheFacade USER_VERIFY_CODE = new AuthoritySystemCache("AUTHORITY:SYSTEM:USER:LOGIN:");
    public static CacheFacade USER_ACCESS_TOKEN = new AuthoritySystemCache("AUTHORITY:SYSTEM:USER:ACCESS_TOKEN:");



    protected AuthoritySystemCache(String keyPrefix) {
        super(keyPrefix);
    }


}
