package com.onnoa.mybatis.source.analysis.entity;

import com.onnoa.mybatis.source.analysis.enums.UserType;

import java.util.List;

/**
 * @className: User
 * @description:
 * @author: onnoA
 * @date: 2021/9/28
 **/
public class User {

    /**
     * 用户id，主键
     */
    private String id;

    private String username;

    /**
     * 用户号码
     */
    private String svcnum;

    private String password;

    /**
     * 一个用户只能对应一个客户
     */
    private Cust cust;

    /**
     * 一个用户可以有多个账户
     */
    private List<Acct> accts;

    /**
     * 用户类型，两种：普通用户和重要用户
     */
    private UserType type;


    public User(String id, String username) {
        super();
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cust getCust() {
        return cust;
    }

    public void setCust(Cust cust) {
        this.cust = cust;
    }

    public List<Acct> getAccts() {
        return accts;
    }

    public void setAccts(List<Acct> accts) {
        this.accts = accts;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getSvcnum() {
        return svcnum;
    }

    public void setSvcnum(String svcnum) {
        this.svcnum = svcnum;
    }


}
