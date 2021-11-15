package com.onnoa.shop.common.pattern.factory.abstractfactory.demo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaleHumanFactory extends AbstractHumanFactory {

    @Override
    Human createWhiteHuman() {
        return new WhiteMaleHuman();
    }

    @Override
    Human createYellowHuman() {
        return new YellowMaleHuman();
    }

    @Override
    Human createBlackHuman() {
        return new BlackMaleHuman();
    }

    private static final String URLHIVE = "jdbc:hive2://172.16.198.21:10000/hive";
    private static Connection connection = null;


    public static Connection getHiveConnection() {
        if (null == connection) {
//            synchronized (HiveManage.class) {
                if (null == connection) {
                    try {
                        Class.forName("org.apache.hive.jdbc.HiveDriver");
                        connection = DriverManager.getConnection(URLHIVE, "hive", "hive");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
//            }
        }
        return connection;
    }


    public static void main(String args[]) throws SQLException {

//    	String sql = "select ipaddress,count(ipaddress) as count from apachelog "
//    			+ "group by ipaddress order by count desc";
        String sql1="select 1 from MCC_RE_BIHAVIOR limit 1";
        PreparedStatement pstm = getHiveConnection().prepareStatement(sql1);
        ResultSet rs= pstm.executeQuery(sql1);

        while (rs.next()) {
            System.out.println(rs.getString(1)+"	"+rs.getString(2)+
                    "		"+rs.getString(3)+"		"+rs.getString(4));
        }
        pstm.close();
        rs.close();

    }

}
