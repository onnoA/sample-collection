package com.onnoa.shop.mybatis.plus.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

import java.util.List;

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
        long id = 34914;
        OcrCustomerOrder entity = customerOrderService.getById(id);
        if (entity != null) {
            boolean b = customerOrderService.removeById(entity);
            log.info("插入是否成功:{}", Boolean.TRUE == b ? "是" : "否");
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
        log.info("分页返回1:{} \n 分页返回2 :{}", customerOrderPage, orderPage);


    }

}
