package com.onnoa.spring.configuration.metadata.config.strategy;

import com.onnoa.spring.configuration.metadata.config.dto.OneUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@BusinessProcessAnnotation(type = BusinessStrategyConstant.DemoOne.HAHA, source = BusinessStrategyConstant.DemoOne.ONE)
public class DemoOneImpl implements BusinessProcessFactory<OneUser, Long> {

    @Override
    public OneUser businessProcess(Long id) {
        OneUser oneUser = new OneUser();
        System.out.println("id : " + id + "..........");
        oneUser.setUsername("zh");
        oneUser.setPassword("123456");
        return oneUser;
    }
}
