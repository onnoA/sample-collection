package com.onnoa.distributed.primary.key.distributekeystrategy;

/**
 * @Description: 分布式主键生成策略接口
 * @Author: onnoA
 * @Date: 2020/8/8 14:43
 */
public interface IDistributedKeyStrategy {

    public String generate();

}
