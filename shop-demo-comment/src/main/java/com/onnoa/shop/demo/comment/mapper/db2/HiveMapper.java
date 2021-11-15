package com.onnoa.shop.demo.comment.mapper.db2;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HiveMapper {

    List<Map<String, Object>> selectList();
}
