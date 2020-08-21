package com.onnoa.shop.demo.authority.system.dto;

import lombok.Data;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/7/23 11:27
 */
@Data
public class FrontViewResourceDto extends BaseSysFrontViewResourceDto {

    /**
     * 父级资源名字
     */
    private String parentName;

    /**
     * 后端接口url
     */
    private String interfaceUrl;
}
