package com.onnoa.shop.common.pattern.builder.demo5;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月02日 9:49
 */

@Builder
@ToString
public class User {
    private final Integer code = 200;
    private String username;
    private String password;

    @Singular(value = "listTest")
    private List<String> list;
}
