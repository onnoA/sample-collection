import cn.quellanan.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        String dirver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://127.0.0.1:3306/sample_collections?characterEncoding=UTF8&serverTimezone=GMT%2B8&allowMultiQueries=true";
        String userName="root";
        String password="123456";

        Connection connection=null;
        List<User> userList=new ArrayList<>();
        try {
            Class.forName(dirver);
            connection= DriverManager.getConnection(url,userName,password);

            String sql="select * from user where username=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"张三");
            System.out.println(sql);
            ResultSet resultSet=preparedStatement.executeQuery();

            User user=null;
            while(resultSet.next()){
                user=new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (!userList.isEmpty()) {
            for (User user : userList) {
                System.out.println(user.toString());
            }
        }

    }

}
