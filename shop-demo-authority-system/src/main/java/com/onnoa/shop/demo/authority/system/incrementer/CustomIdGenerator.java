package com.onnoa.shop.demo.authority.system.incrementer;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/7/5 14:56
 */
@Slf4j
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Long nextId(Object entity) {
        IdentifierGenerator identifierGenerator=new DefaultIdentifierGenerator();
        long number = (long) identifierGenerator.nextId(entity);
        log.info("生成主键值->:{}", number);
        return number;
    }


}
