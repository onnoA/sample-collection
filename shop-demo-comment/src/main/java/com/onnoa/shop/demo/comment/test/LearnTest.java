package com.onnoa.shop.demo.comment.test;

import com.alibaba.fastjson.JSON;
import com.onnoa.shop.demo.comment.mapper.db1.MysqlMapper;
import com.onnoa.shop.demo.comment.mapper.db2.HiveMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LearnTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    HiveMapper hiveMapper;
    @Resource
    MysqlMapper mysqlMapper;


    @Test
    public void Test() {
        List<Map<String, Object>> list = hiveMapper.selectList();
        System.out.println(JSON.toJSON(list));
    }

    @Test
    public void Test2() {
        List<Map<String, Object>> list = mysqlMapper.selectList();
        System.out.println(JSON.toJSON(list));
    }

    @Test
    public void test() {
        String sql = "CREATE TABLE `consumer_monitor_bk` (\n"
                + "  `id` bigint(16) NOT NULL COMMENT '主键',\n" + "  `day_id` date DEFAULT NULL COMMENT '日期',\n"
                + "  `event_name` varchar(64) DEFAULT NULL,\n"
                + "  `receive_num` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '总数据量',\n"
                + "  `success_num` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '成功数',\n"
                + "  `stacked_num` int(6) DEFAULT NULL COMMENT '堆积数',\n"
                + "  `failed_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '失败数',\n"
                + "  `event_code` varchar(64) DEFAULT NULL,\n" + "  PRIMARY KEY (`id`)\n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消费监控表';";
        int update = jdbcTemplate.update(sql);
        System.out.println("执行完成:" + update);

    }

}
