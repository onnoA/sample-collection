package com.onnoa.shop.demo.authority.system.dto;

import com.onnoa.shop.common.dto.PageParams;

import lombok.Data;

@Data
public class UserReqDto extends PageParams {

    private String phoneOrUserName;

}
