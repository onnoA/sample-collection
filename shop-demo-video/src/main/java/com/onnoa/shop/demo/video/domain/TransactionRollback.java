package com.onnoa.shop.demo.video.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: TransactionTollback
 * @description:
 * @author: onnoA
 * @date: 2021/9/17
 **/
@Data
@TableName(value = "transaction_rollback")
public class TransactionRollback implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String desc;

    private String phone;

}
