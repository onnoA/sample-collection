package com.onnoa.shop.common.utils.jwt;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/7/7 00:27
 */
@Data
@Accessors(chain = true)
public class JWtObj {
    private int id;
    private String username;
}
