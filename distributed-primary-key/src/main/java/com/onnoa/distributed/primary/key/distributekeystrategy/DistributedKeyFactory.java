package com.onnoa.distributed.primary.key.distributekeystrategy;

import com.onnoa.distributed.primary.key.enums.KeyTypeEnum;
import com.onnoa.shop.common.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/8/8 14:47
 */
@Service
public class DistributedKeyFactory {

    private static final Logger logger = LoggerFactory.getLogger(DistributedKeyFactory.class);

    public static IDistributedKeyStrategy getInstance(int keyType) {
        KeyTypeEnum keyTypeEnum = KeyTypeEnum.getByKey(keyType);
        logger.info("keyTypeEnum:" + keyTypeEnum);
        switch (keyTypeEnum) {
            case REDIS:
                return SpringContextHolder.getBean(RedisDistributeKeyStrategy.class);
            case SNOWFLAKE:
                return SpringContextHolder.getBean(SnowFlakeDistributeKeyStrategy.class);
            case UID_GENERATOR:
                //return GetBeanUtil.getBean(YarnResourceStrategy.class);
            case LEAF:
                //return GetBeanUtil.getBean(HbaseResourceStrategy.class);
            case OTHER:
                //return GetBeanUtil.getBean(OtherResourceStrategy.class);
            default:
                break;
        }
        return null;
    }
}
