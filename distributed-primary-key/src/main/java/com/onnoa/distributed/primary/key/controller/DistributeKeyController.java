package com.onnoa.distributed.primary.key.controller;

import java.util.List;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onnoa.distributed.primary.key.distributekeystrategy.DistributedKeyFactory;
import com.onnoa.distributed.primary.key.distributekeystrategy.IDistributedKeyStrategy;
import com.onnoa.shop.common.result.ResultBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/8/8 16:22
 */
@RestController
@RequestMapping(value = "/distribute/key")
@Slf4j
public class DistributeKeyController {

    @PostMapping(value = "/generate")
    public ResultBean distributeKeyTest() {
        List<String> list = Lists.newArrayList();
        for (int i = 1; i < 3; i++) {
            IDistributedKeyStrategy instance = DistributedKeyFactory.getInstance(i);
            list.add(instance.generate());
        }

        return ResultBean.success(list);

    }

    @PostMapping(value = "option")
    public ResultBean option(@RequestParam String value){


        return null;
    }

    public void cir (){
        for (int i=0;i<10;i++){
            if(i == 4){
                return;
            }

        }
    }
}
