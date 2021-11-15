package com.onnoa.myshop.springcloud.provider.after.test.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoDto implements Serializable {

    private String username;

    private String password;
}
