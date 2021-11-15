package com.onnoa.shop.demo.authority.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author onnoA Spring Validator 分组校验测试
 * @date 2021年05月24日 10:47
 */
@Data
public class PacketCheckDto implements Serializable {

    @NotBlank(message = "id不能为空", groups = {Add.class})
    private String id;

    private String username;

    private String password;

    //    - ^string : 匹配以 string 开头的字符串
//- string$ ：匹配以 string 结尾的字符串
//- ^string$ ：精确匹配 string 字符串
//- ((^Man$|^Woman$|^UGM$)) : 值只能在 Man,Woman,UGM 这三个值中选择
    @Pattern(regexp = "((^Man$|^Woman$|^UGM$))", message = "sex 值不在可选范围", groups = {Add.class})
    private String sex;

    public interface Add {

    }

    public interface Update {

    }
}
