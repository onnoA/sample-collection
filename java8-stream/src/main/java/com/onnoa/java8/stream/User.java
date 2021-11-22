package com.onnoa.java8.stream;

/**
 * @className: User
 * @description:
 * @author: onnoA
 * @date: 2021/11/19
 **/

public class User {

    private String username;

    private String address;

    private int age;

    public User() {
    }

    public User(String username, String address, int age) {
        this.username = username;
        this.address = address;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}
