package com.onnoa.shop.mybatis.plus.modules.sample_collections.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author onnoA
 * @since 2021-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("transaction_rollback")
public class TransactionRollback implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String desc;

    private String phone;


}
