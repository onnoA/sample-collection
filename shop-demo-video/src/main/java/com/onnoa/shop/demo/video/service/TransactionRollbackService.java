package com.onnoa.shop.demo.video.service;

import com.onnoa.shop.demo.video.domain.TransactionRollback;

/**
 * @className: TransactionRollbackService
 * @description:
 * @author: onnoA
 * @date: 2021/9/17
 **/
public interface TransactionRollbackService {

    int tableUpdate(TransactionRollback transactionRollback);
}
