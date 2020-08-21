package com.onnoa.shop.demo.authority.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/7/20 01:05
 */
@Data
public class ButtonListDto implements Serializable {

    @NotBlank(message = "用户名不能为空。")
    private String username;

    @NotBlank(message = "父id不能为空。")
    private String parentId;

}
