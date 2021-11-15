package com.onnoa.shop.demo.video.service.impl;

import com.onnoa.shop.demo.video.domain.TransactionRollback;
import com.onnoa.shop.demo.video.exception.ServiceException;
import com.onnoa.shop.demo.video.mapper.TransactionRollbackMapper;
import com.onnoa.shop.demo.video.service.TransactionRollbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @className: TransactionRollbackServiceImpl
 * @description:
 * @author: onnoA
 * @date: 2021/9/17
 **/
@Service
@Slf4j
public class TransactionRollbackServiceImpl implements TransactionRollbackService {

    @Autowired
    private TransactionRollbackMapper transactionRollbackMapper;


    @Override
    @Transactional
    public int tableUpdate(TransactionRollback transactionRollback) {
        int result = 0;
        if (transactionRollback != null && transactionRollback.getId() != null) {
            result = transactionRollbackMapper.updateById(transactionRollback);
        } else {
            result = transactionRollbackMapper.insertEntity(transactionRollback);
        }
        if (result > 0) {
//            throw new RuntimeException("事务回滚..");
//            throw ServiceException.NEW_EXCEPTION_INSTANCE_FAILED;
            throw ServiceException.TRANSACTION_ROLLBACK.newInstance(false, "事务回滚..");
        }
        return result;
    }
}
