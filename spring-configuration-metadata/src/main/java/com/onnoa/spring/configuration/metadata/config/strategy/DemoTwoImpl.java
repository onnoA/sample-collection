package com.onnoa.spring.configuration.metadata.config.strategy;

import com.onnoa.spring.configuration.metadata.config.dto.OneUser;
import com.onnoa.spring.configuration.metadata.config.dto.TwoUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@BusinessProcessAnnotation(type = BusinessStrategyConstant.DemoOne.HEHE, source = BusinessStrategyConstant.DemoOne.TWO)
public class DemoTwoImpl implements BusinessProcessFactory<TwoUser, String>  {


    @Override
    public TwoUser businessProcess(String name) {
        TwoUser twoUser = new TwoUser();
        System.out.println("name : " + name + "..........");
        twoUser.setName("zh");
        twoUser.setPwd("123456");
        return twoUser;
    }
}
