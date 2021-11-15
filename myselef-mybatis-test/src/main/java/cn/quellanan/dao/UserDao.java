package cn.quellanan.dao;

import cn.quellanan.pojo.User;

import java.util.List;

public interface UserDao {


    List<User> selectAll();

    List<User> selectByName(User user);

    User selectById(User user);

    int add(User user);

    int update(User user);

    int delete(int id);



}
