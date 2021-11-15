package com.onnoa.shop.demo.upload.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

    public static void main(String[] args) throws SQLException {
        //
        String url = "jdbc:mysql://127.0.0.1:3306/sample_collections?characterEncoding=UTF8&serverTimezone=GMT%2B8&allowMultiQueries=true";
        Connection conn = DriverManager.getConnection(url, "root", "123456");
        Statement stat = conn.createStatement();
        // 创建数据库hello
        int count = stat.executeUpdate("CREATE TABLE `consumer_monitor_bak` (\n"
            + "  `id` bigint(16) NOT NULL COMMENT '主键',\n" + "  `day_id` date DEFAULT NULL COMMENT '日期',\n"
            + "  `event_name` varchar(64) DEFAULT NULL,\n"
            + "  `receive_num` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '总数据量',\n"
            + "  `success_num` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '成功数',\n"
            + "  `stacked_num` int(6) DEFAULT NULL COMMENT '堆积数',\n"
            + "  `failed_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '失败数',\n"
            + "  `event_code` varchar(64) DEFAULT NULL,\n" + "  PRIMARY KEY (`id`)\n"
            + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消费监控表';");
        System.out.println("创建表的数量: " + count);
    }
}
