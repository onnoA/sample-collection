package com.onnoa.shop.demo.video.service.impl;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.onnoa.shop.demo.video.mybatis.MyBatisUtil;
import com.onnoa.shop.demo.video.service.MybatisSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: MybatisSqlServiceImpl
 * @description:
 * @author: onnoA
 * @date: 2021/9/27
 **/
@Service
public class MybatisSqlServiceImpl implements MybatisSqlService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map parse(Map<String, Object> map) {
        JSONObject object = new JSONObject();
        //.put("condition","3");
        HashMap<String, Object> map2 = new HashMap();
//        map2.put("condition", "3");
        //map2.put("condition",object);
        Long[] k = new Long[3];
        k[0] = 1l;
        k[1] = 2l;
        k[2] = 3l;
//        object .put("array",k);
//        map2.put("array", object);
//        object.put("title", "测试");
        map2.put("title", "测试");
//        map2.put("title", "测试");
        String sql = MyBatisUtil.changeDynamicSql2StaticSql(MapUtil.getStr(map, "sql"), map2);
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        map.clear();
        map.put("result", count);
        return map;
    }
}
