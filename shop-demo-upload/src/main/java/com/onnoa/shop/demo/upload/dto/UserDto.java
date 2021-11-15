package com.onnoa.shop.demo.upload.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private String username;

    private int age;

    private String mobile;
}
