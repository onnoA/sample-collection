package com.onnoa.shop.demo.comment.mapper.db1;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MysqlMapper {

    List<Map<String, Object>> selectList();
}
