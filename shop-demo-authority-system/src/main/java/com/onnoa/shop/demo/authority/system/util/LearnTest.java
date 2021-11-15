package com.onnoa.shop.demo.authority.system.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LearnTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

//    public static void main(String[] args) throws SQLException {
//        //
//        String url = "jdbc:mysql://172.16.68.50:3306/recommend_service?allowMultiQueries=false&noAccessToProcedureBodies=true&useSSL=false&rewriteBatchedStatements=true&characterEncoding=utf-8";
//        Connection conn = DriverManager.getConnection(url, "rs", "Rs@12345");
//        Statement stat = conn.createStatement();
//
    // 创建数据库hello
//        int count = stat.executeUpdate("CREATE TABLE `consumer_monitor_bk` (\n"
//            + "  `id` bigint(16) NOT NULL COMMENT '主键',\n" + "  `day_id` date DEFAULT NULL COMMENT '日期',\n"
//            + "  `event_name` varchar(64) DEFAULT NULL,\n"
//            + "  `receive_num` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '总数据量',\n"
//            + "  `success_num` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '成功数',\n"
//            + "  `stacked_num` int(6) DEFAULT NULL COMMENT '堆积数',\n"
//            + "  `failed_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '失败数',\n"
//            + "  `event_code` varchar(64) DEFAULT NULL,\n" + "  PRIMARY KEY (`id`)\n"
//            + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消费监控表';" );
//        System.out.println("创建表的数量: " + count);
//    }
}
