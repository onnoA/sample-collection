package com.onnoa.shop.demo.video.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.onnoa.shop.demo.video.domain.TransactionRollback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @className: TransactionRollbackMapper
 * @description:
 * @author: onnoA
 * @date: 2021/9/17
 **/
@Mapper
public interface TransactionRollbackMapper extends BaseMapper<TransactionRollback> {

    int insertEntity(@Param("entity") TransactionRollback entity);

    Map queryTest(Map map);


}
