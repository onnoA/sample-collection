package com.onnoa.mybatis.source.analysis.dao;

import com.onnoa.mybatis.source.analysis.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @className: UserMapper
 * @description:
 * @author: onnoA
 * @date: 2021/9/28
 **/
public interface UserMapper {

    @Select("select user_id,user_name,cust_id from tf_user where user_id = #{userId} and user_name = #{userName}")
    List<User> getUserList(String userId, String userName);

//    @Select("select user_id,user_name,cust_id from tf_user where user_id = #{userId} and user_name = #{userName}")
//    List<User> getUserList(Map<String, Object> param);


}
