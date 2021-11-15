package com.onnoa.shop.common.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月09日 17:26
 */
public class HiveTest {
    public static final String driverName = "org.apache.hive.jdbc.HiveDriver";

    /**
     *       * 获取连接 
     *      
     */
    public Connection getConnection(String url, String userName, String password) {
        try {
            Class.forName(driverName);
            Connection conn = DriverManager.getConnection(url, userName, password);
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


//    public static void main(String args[]) {
//        Connection con = new HiveTest().getConnection(
//                "jdbc:hive2://172.16.198.21:10000/hive", "hive", "hive"
//        );
//        try {
//            Statement stmt = con.createStatement();
//            String sql = "SELECT 1 FROM  libya_re_behavior limit 1";
//
//            ResultSet res = stmt.executeQuery(sql);
//            while (res.next()) {
//                sql = "select * from " + res.getString(1);
//                System.out.println("tables:" + res.getString(1));
//                ResultSet resTable = stmt.executeQuery(sql);
//                while (resTable.next()) {
//                    System.out.println(resTable.getString(2));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String args[]) {
//        Connection con = new HiveTest().getConnection("jdbc:hive2://10.198.217.4:10000/default", "hive", "hive");
        Connection con = new HiveTest().getConnection("jdbc:hive2://172.16.198.22:10000/hive", "hive", "hive");
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT 1 FROM  LIBYA_RE_OFFER limit 1";

            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                sql = "select * from " + res.getString(1);
                System.out.println("tables:" + res.getString(1));
                ResultSet resTable = stmt.executeQuery(sql);
                while (resTable.next()) {
                    System.out.println(resTable.getString(2));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
