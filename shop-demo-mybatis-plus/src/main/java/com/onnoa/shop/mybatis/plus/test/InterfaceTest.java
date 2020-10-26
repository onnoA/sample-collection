package com.onnoa.shop.mybatis.plus.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.onnoa.shop.mybatis.plus.modules.ocr.domain.OcrCustomerOrder;
import com.onnoa.shop.mybatis.plus.modules.ocr.mapper.OcrCustomerOrderMapper;
import com.onnoa.shop.mybatis.plus.modules.ocr.service.OcrCustomerOrderService;
import com.onnoa.shop.mybatis.plus.modules.ocr.service.impl.OcrCustomerOrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class InterfaceTest {

    @Autowired
    private OcrCustomerOrderService customerOrderService;
    @Autowired
    private OcrCustomerOrderServiceImpl serviceImpl;

    @Autowired
    private OcrCustomerOrderMapper customerOrderMapper;

    @Test
    public void insertTest() {
        OcrCustomerOrder entity = new OcrCustomerOrder();
        entity.setMonthId(202010);
        entity.setCustOrderId("874620102020185314");
        entity.setFileId("de181bd67e5c36145d8684eb36e8bcb1_0");
        entity.setCertiNumber("431127199110162818");
        Boolean isSuccess = customerOrderService.save(entity);
        log.info("插入是否成功:{}", Boolean.TRUE == isSuccess ? "是" : "否");
    }

    @Test
    public void delTest() {
        long id = 34914l;
        OcrCustomerOrder entity = customerOrderService.getById(id);
        if (entity != null) {
            boolean b = customerOrderService.removeById(entity);
            log.info("删除是否成功:{}", Boolean.TRUE == b ? "是" : "否");
        } else {
            log.info("此数据不存在,id 为 :{}", id);
        }

    }

    @Test
    public void updateTest() {
        long id = 34915;
        OcrCustomerOrder entity = customerOrderService.getById(id);
        if (entity != null) {
            entity.setFileId("修改后的fileId");
            boolean b = customerOrderService.updateById(entity);
            log.info("修改是否成功:{}", Boolean.TRUE == b ? "是" : "否");
        } else {
            log.info("此数据不存在,id 为 :{}", id);
        }
    }

    @Test
    public void queryTest() {
        List<OcrCustomerOrder> list = customerOrderService.list();
        list.stream().forEach(entity -> System.out.println(entity));
    }

    @Test
    public void pageTest() {
        Page<OcrCustomerOrder> page = new Page<>(1, 10);
        QueryWrapper<OcrCustomerOrder> qw = new QueryWrapper<>();
        qw.lambda().orderByDesc(OcrCustomerOrder::getGmtCreate);
        Page<OcrCustomerOrder> customerOrderPage = customerOrderService.page(page, qw);
        Page<OcrCustomerOrder> orderPage = customerOrderMapper.selectPage(page, qw);
        Optional<OcrCustomerOrder> first = customerOrderPage.getRecords().parallelStream().findFirst();
        log.info("分页返回1:{} \n 分页返回2 :{}", customerOrderPage, orderPage);
    }

    @Test
    public void filterTest() {
        List<OcrCustomerOrder> list = customerOrderService.list();
        List<String> idList = list.parallelStream().map(entity -> entity.getCustOrderId()).collect(Collectors.toList());
        List<String> tempList = Lists.newArrayList();
        List<String> repeatList = Lists.newArrayList();
        idList.parallelStream().forEach(str -> {
                    if (tempList.contains(str)) {
                        repeatList.add(str);
                    } else {
                        tempList.add(str);
                    }
                }
        );
        List<OcrCustomerOrder> repeatDto = Lists.newArrayList();
        //List<OcrCustomerOrder> orders = list.parallelStream().filter(entity -> repeatList.contains(entity.getCustOrderId())).collect(Collectors.toList());
        Iterator<OcrCustomerOrder> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (repeatList.contains(iterator.next().getCustOrderId())) {
                repeatDto.add(iterator.next());
                iterator.remove();
            }
        }
        log.info("移除掉重复集合的集合:{}", list);
        log.info("重复的集合:{}", repeatDto);
        //log.info("总的集合:{},去重后的集合:{},重复的集合:{}", idList, tempList, repeatList);

    }

//    $ javac Java8Tester.java
//$ java Java8Tester
//第一个参数值存在: false
//第二个参数值存在: true
//10

    public static void main(String[] args) {
//        OcrCustomerOrder order = new OcrCustomerOrder();
//        Optional.ofNullable(order);
        InterfaceTest java8Tester = new InterfaceTest();
        Integer value1 = null;
        Integer value2 = 1;// new Integer(10);

        // Optional.ofNullable - 允许传递为 null 参数
        Optional<Integer> a = Optional.ofNullable(value1);

        // Optional.of - 如果传递的参数是 null，抛出异常 NullPointerException
        Optional<Integer> b = Optional.of(value2);
        System.out.println(java8Tester.sum(a, b));

    }

    public Integer sum(Optional<Integer> a, Optional<Integer> b) {

        // Optional.isPresent - 判断值是否存在

        System.out.println("第一个参数值存在: " + a.isPresent());
        System.out.println("第二个参数值存在: " + b.isPresent());

        // Optional.orElse - 如果值存在，返回它，否则返回默认值
        Integer value1 = a.orElse(0);

        //Optional.get - 获取值，值需要存在
        Integer value2 = b.get();
        return value1 + value2;
    }


}
