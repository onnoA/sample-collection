package com.onnoa.shop.demo.video.controller;

import com.onnoa.shop.demo.video.common.result.ResultBean;
import com.onnoa.shop.demo.video.domain.TransactionRollback;
import com.onnoa.shop.demo.video.service.TransactionRollbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: TransactionRollbackController
 * @description: Spring 事务回滚控制层
 * @author: onnoA
 * @date: 2021/9/17
 **/
@RestController
@RequestMapping(value = "/transaction")
public class TransactionRollbackController {

    @Autowired
    private TransactionRollbackService transactionRollbackService;

    @PostMapping(value = "/rollback")
    public ResultBean rollback(@RequestBody TransactionRollback transactionRollback) {
        int i = transactionRollbackService.tableUpdate(transactionRollback);
        return ResultBean.success(i);
    }


}
