package com.onnoa.shop.demo.authority.system.dto;

import com.onnoa.shop.common.dto.PageReqParams;

import lombok.Data;

@Data
public class UserReqDto extends PageReqParams {

    private String phoneOrUserName;

}
