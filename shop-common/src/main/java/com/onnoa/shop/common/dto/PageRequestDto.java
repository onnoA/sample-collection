package com.onnoa.shop.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequestDto implements Serializable {

    private static final int DEFAULT_NO = 1;
    private static final int DEFAULT_SIZE = 20;
    private Integer pageNo = DEFAULT_NO;
    private Integer pageSize = DEFAULT_SIZE;
}
