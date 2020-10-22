package com.onnoa.shop.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams implements Serializable {
    private static final int DEFAULT_NO = 1;

    private static final int DEFAULT_SIZE = 20;

    private Integer pageNo = DEFAULT_NO;

    private Integer pageSize = DEFAULT_SIZE;

}
